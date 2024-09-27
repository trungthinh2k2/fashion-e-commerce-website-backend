package iuh.fit.fashionecommercewebsitebackend.utils;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidToken {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public void validToken(Long id, HttpServletRequest request) throws DataNotFoundException {
        String token = request.getHeader("Authorization").substring(7);
        User user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Invalid token"));
        if(!user.getEmail().equals(jwtService.extractEmail(token))) {
            throw new DataNotFoundException("Invalid token");
        }
    }

    public void validToken(String email, HttpServletRequest request) throws DataNotFoundException {
        String token = request.getHeader("Authorization").substring(7);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Invalid token"));
        if(!user.getEmail().equals(jwtService.extractEmail(token))) {
            throw new DataNotFoundException("Invalid token");
        }
    }
}
