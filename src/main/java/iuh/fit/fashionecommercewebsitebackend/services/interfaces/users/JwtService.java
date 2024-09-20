package iuh.fit.fashionecommercewebsitebackend.services.interfaces.users;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
}
