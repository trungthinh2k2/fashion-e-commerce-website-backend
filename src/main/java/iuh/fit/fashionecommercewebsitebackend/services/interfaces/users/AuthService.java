package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.LoginRequestDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserRegisterDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.VerifyEmailDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws Exception;
    void verifyEmail(VerifyEmailDto verifyEmailDto) throws Exception;
    void login(LoginRequestDto loginRequestDto, HttpServletResponse response) throws Exception;
}
