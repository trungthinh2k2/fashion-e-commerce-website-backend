package iuh.fit.fashionecommercewebsitebackend.api.controllers.auth;

import io.swagger.v3.oas.annotations.Operation;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.*;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.GeneralResponse;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @CreateResponse
    @PostMapping("/register")
    public Response register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws Exception {
        authService.register(userRegisterDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "User registered successfully",
                "Check OTP in your email"
        );
    }

    @Operation(summary = "Xác thực tài khoản", description = "Tài khoản đã được tạo, cần xác thực email để kích hoạt tài khoản")
    @GeneralResponse
    @PostMapping("/verify-email")
    public Response verifyEmail(@RequestBody VerifyEmailDto verifyEmailDto) throws Exception {
        authService.verifyEmail(verifyEmailDto);
        return new ResponseSuccess<>(
            HttpStatus.OK.value(),
            "Email verified successfully",
            "You can now login"
        );
    }

    @Operation(summary = "Đăng nhâp", description = "Sử dụng email và password để đăng nhập vào hệ thống")
    @GeneralResponse
    @PostMapping("/login")
    public Response login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) throws Exception {
        authService.login(loginRequestDto, response);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Login successfully",
                null
        );
    }

    @Operation(summary = "Refresh Token", description = "Sử dụng refresh token để lấy access token mới")
    @GeneralResponse
    @PostMapping("/refresh-token")
    public Response refreshToken(@RequestBody String refreshToken, HttpServletResponse response) throws Exception {
        authService.refreshToken(refreshToken, response);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                 "Refresh token successfully",
                null
        );
    }

    @Operation(summary = "Gửi email xác thực", description = "Gửi email xác thực để đặt lại mật khẩu khi bị quên mật khẩu")
    @GeneralResponse
    @PostMapping("/send-verification-email")
    public Response sendVerificationEmail(@RequestBody String email) throws Exception {
        authService.forgotPassword(email);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Send verification email successfully",
                "Check your email"
        );
    }

    @Operation(summary = "Xác thực email để đặt lại mật khẩu", description = "Xác thực email để đặt lại mật khẩu khi bị quên mật khẩu")
    @GeneralResponse
    @PostMapping("/verify-email-otp-reset-password")
    public Response verifyEmailOtpResetPassword(@RequestBody VerifyEmailDto verifyEmailDto) throws Exception {
        authService.verifyEmailOTPResetPassword(verifyEmailDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Verify email OTP reset password successfully",
                null
        );
    }

    @Operation(summary = "Đặt lại mật khẩu", description = "Đặt lại mật khẩu khi bị quên mật khẩu")
    @GeneralResponse
    @PostMapping("/reset-password")
    public Response resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) throws Exception {
        authService.resetPassword(resetPasswordDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Reset password successfully",
                null
        );
    }

    @Operation(summary = "Đăng xuất", description = "Đăng xuất tài khoản")
    @GeneralResponse
    @PostMapping("/logout")
    public Response logout(@RequestBody String refreshToken) throws Exception {
        authService.logout(refreshToken);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Logout successfully",
                null
        );
    }

}
