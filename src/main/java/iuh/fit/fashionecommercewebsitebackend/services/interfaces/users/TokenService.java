package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import iuh.fit.fashionecommercewebsitebackend.models.Token;
import iuh.fit.fashionecommercewebsitebackend.models.User;

public interface TokenService {
    void saveToken(User user, Token token);
}
