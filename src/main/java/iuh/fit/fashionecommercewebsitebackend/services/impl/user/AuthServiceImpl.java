package iuh.fit.fashionecommercewebsitebackend.services.impl.user;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserRegisterDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.EmailService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.AuthService;
import iuh.fit.fashionecommercewebsitebackend.utils.EmailDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final EmailService emailService;

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
                .password(userRegisterDto.getPassword())
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
