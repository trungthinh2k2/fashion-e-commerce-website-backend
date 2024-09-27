package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.*;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws Exception;
    void verifyEmail(VerifyEmailDto verifyEmailDto) throws Exception;
    void login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws Exception;
    void refreshToken(String refreshToken, HttpServletResponse response) throws DataExistsException, DataNotFoundException;
    void forgotPassword(String email) throws Exception;
    void verifyEmailOTPResetPassword(VerifyEmailDto verifyEmailDto) throws Exception;
    void resetPassword(ResetPasswordDto resetPasswordDto) throws Exception;
    void changePassword(ChangePasswordDto changePasswordDto) throws Exception;
    void logout(String refreshToken) throws Exception;
}
