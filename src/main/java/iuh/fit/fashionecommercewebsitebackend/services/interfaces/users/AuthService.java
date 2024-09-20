package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserRegisterDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;

public interface AuthService {
    void register(UserRegisterDto userRegisterDto) throws Exception;
}
