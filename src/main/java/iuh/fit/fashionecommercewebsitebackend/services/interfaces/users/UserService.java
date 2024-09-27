package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface UserService extends BaseService<User, Long>{
    User getUserByEmail(String email) throws DataNotFoundException;
    User updateUser(String email, UserUpdateDto userUpdateDto) throws DataNotFoundException;
    User updateAvatar(String email, String avatarUrl) throws DataNotFoundException;
}