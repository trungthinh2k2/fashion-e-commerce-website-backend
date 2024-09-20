package iuh.fit.fashionecommercewebsitebackend.api.controllers.auth;

import io.swagger.v3.oas.annotations.Operation;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.LoginRequestDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserRegisterDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.VerifyEmailDto;
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
                "Đăng nhập thành công",
                null
        );
    }

}
