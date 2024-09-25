package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface UserService extends BaseService<User, Long>{
    User getUserByEmail(String email);
    User updateUser(String email, UserUpdateDto userUpdateDto);
    User updateAvatar(String email, String avatarUrl);
}