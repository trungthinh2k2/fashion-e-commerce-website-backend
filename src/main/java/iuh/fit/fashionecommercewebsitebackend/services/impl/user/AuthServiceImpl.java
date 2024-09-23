package iuh.fit.fashionecommercewebsitebackend.services.impl.user;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.LoginRequestDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.ResetPasswordDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserRegisterDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.VerifyEmailDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.CustomUserDetails;
import iuh.fit.fashionecommercewebsitebackend.models.Token;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import iuh.fit.fashionecommercewebsitebackend.repositories.TokenRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.EmailService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.AuthService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.JwtService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.TokenService;
import iuh.fit.fashionecommercewebsitebackend.utils.EmailDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(UserRegisterDto userRegisterDto) throws Exception {
        User user = mapperToUser(userRegisterDto);
        userRepository.save(user);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Mã OTP xác thực để đăng ký tài khoản")
                .msgBody("Mã OTP của bạn là: " + user.getOtp())
                .build();
        emailService.sendHtmlMail(emailDetails);

    }

    @Override
    public void verifyEmail(VerifyEmailDto verifyEmailDto) throws Exception {
        String email = verifyEmailDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Email not found"));
        if (user.getOtp().equals(verifyEmailDto.getOtp())) {
            user.setVerify(true);
            user.setOtp(null);
            userRepository.save(user);
        } else {
            throw new DataExistsException("OTP is not correct");
        }
    }

    @Override
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws Exception {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Email not found"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new DataNotFoundException("Password is not correct");
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        Token token = new Token();
        token.setRefreshToken(refreshToken);
        token.setUser(user);
        token.setIssueDate(LocalDateTime.now());
        tokenService.saveToken( user,token);

        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60); // 30 phút
        response.addCookie(cookie);
    }

    @Override
    public void refreshToken(String refreshToken, HttpServletResponse response) throws DataExistsException {
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new DataNotFoundException("Refresh token not found"));
        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new DataNotFoundException("User is not exists")
        );
        CustomUserDetails userDetail = new CustomUserDetails(user);
        if (!jwtService.validateRefreshToken(refreshToken, userDetail)) {
            tokenRepository.delete(token);
            throw new DataNotFoundException("Refresh token is incorrect");
        }

        token.setRefreshToken(refreshToken);
        token.setUser(user);
        token.setIssueDate(LocalDateTime.now());
        tokenRepository.save(token);

        String accessToken = jwtService.generateToken(userDetail);
        Cookie cookie = new Cookie("accessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 60); // 30 phút
        response.addCookie(cookie);
    }

    @Override
    public void forgotPassword(String email) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Email not found"));
        String otpReset = generateOTP();
        user.setOtpReset(otpReset);
        userRepository.save(user);

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Mã OTP xác thực để đặt lại mật khẩu")
                .msgBody("Mã OTP của bạn là: " + otpReset)
                .build();
        emailService.sendHtmlMail(emailDetails);
    }

    @Override
    public void verifyEmailOTPResetPassword(VerifyEmailDto verifyEmailDto) throws Exception {
        String email = verifyEmailDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Email not found"));
        if (verifyEmailDto.getOtp().equals(user.getOtpReset())) {
            user.setVerify(true);
            userRepository.save(user);
        } else {
            throw new DataNotFoundException("OTP is not correct");
        }
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto, HttpServletResponse response) throws Exception {
        String email = resetPasswordDto.getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Email not found"));
        if (user.getOtpReset() != null && resetPasswordDto.getResetOtp().equals(user.getOtpReset())) {
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
            user.setOtpReset(null);
            user.setOtp(null);
            userRepository.save(user);
            List<Token> tokens = tokenRepository.findAllByUserOrderByIssueDateDesc(user);
            if (!tokens.isEmpty()) {
                tokenRepository.deleteAll(tokens);
            }
            CustomUserDetails userDetails = new CustomUserDetails(user);

            String accessToken = jwtService.generateToken(userDetails);
            String refreshToken = jwtService.generateRefreshToken(userDetails);

            Token token = new Token();
            token.setRefreshToken(refreshToken);
            token.setUser(user);
            token.setIssueDate(LocalDateTime.now());
            tokenService.saveToken( user,token);

            Cookie cookie = new Cookie("accessToken", accessToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 60); // 30 phút
            response.addCookie(cookie);
        } else {
            throw new DataExistsException("OTP is not correct");
        }
    }

    private User mapperToUser(UserRegisterDto userRegisterDto) throws DataExistsException {

        Optional<User> user = userRepository.findByEmail(userRegisterDto.getEmail());
        User userExist = new User();
        if (user.isPresent()) {
            if (user.get().isVerify()) {
                throw new DataExistsException("Email already used");
            }
            userExist = user.get();
        }
        String otp = generateOTP();
        User userResult =  User.builder()
                .email(userRegisterDto.getEmail())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .username(userRegisterDto.getUsername())
                .phone(userRegisterDto.getPhone())
                .role(Role.ROLE_USER)
                .otp(otp)
                .build();
        userResult.setId(userExist.getId());
        return userResult;
    }

    private String generateOTP() {
        Random random = new Random();
        var value = 100000 + random.nextInt(900000);
        return String.valueOf(value);
    }

}
