package iuh.fit.fashionecommercewebsitebackend.services.impl.user;

import iuh.fit.fashionecommercewebsitebackend.models.Token;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.repositories.TokenRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public void saveToken(User user, Token token) {
        List<Token> tokens = tokenRepository.findAllByUserOrderByIssueDateDesc(user);
        if (!tokens.isEmpty() && tokens.size() >= 2) {
            Token tokenToDelete = tokens.get(tokens.size() -1);
            tokenRepository.delete(tokenToDelete);
        }
        tokenRepository.save(token);
    }
}
