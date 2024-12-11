package iuh.fit.fashionecommercewebsitebackend.oauth2;

import iuh.fit.fashionecommercewebsitebackend.models.CustomUserDetails;
import iuh.fit.fashionecommercewebsitebackend.models.Token;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.JwtService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2LoginSuccess implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final TokenService tokenService;

    @Value("${front-end.url}")
    private String frontEndUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        log.info("Login success");
        log.info(authentication.getPrincipal().toString());
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String registrationId = token.getAuthorizedClientRegistrationId();

        User user = new User();
        user.setVerify(true);
        user.setRole(Role.ROLE_USER);

        switch (registrationId) {
            case "google":
                GoogleAccount googleAccount = new GoogleAccount(
                    principal.getName(),
                    principal.getAttribute("email"),
                    principal.getAttribute("name"),
                    principal.getAttribute("picture")
                );
                user.setGoogleAccountId(googleAccount.getAccountId());
                user.setEmail(googleAccount.getEmail());
                user.setUsername(googleAccount.getName());
                user.setAvatarUrl(googleAccount.getPicUrl());
                break;
            case "facebook":
                FacebookAccount facebookAccount = new FacebookAccount(
                    principal.getName(),
                    principal.getAttribute("email"),
                    principal.getAttribute("name")
                );
                user.setFacebookAccountId(facebookAccount.getAccountId());
                user.setEmail(facebookAccount.getEmail());
                user.setUsername(facebookAccount.getName());
                break;
        }
        User userDB = userRepository.findByEmail(user.getEmail()).orElse(user);
        if (userDB.getGoogleAccountId() == null) {
            userDB.setGoogleAccountId(user.getGoogleAccountId());
        }
        if (userDB.getFacebookAccountId() == null) {
            userDB.setFacebookAccountId(user.getFacebookAccountId());
        }

        userRepository.save(userDB);

        CustomUserDetails userDetails = new CustomUserDetails(userDB);

        String accessToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        Token tokenUser = new Token();
        tokenUser.setRefreshToken(refreshToken);
        tokenUser.setUser(userDB);
        tokenUser.setIssueDate(LocalDateTime.now());
        tokenService.saveToken( userDB,tokenUser);

        Cookie cookie = new Cookie("accessToken", accessToken);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(30); // 30s

        Cookie cookieRefresh = new Cookie("refreshToken", refreshToken);
//        cookieRefresh.setHttpOnly(true);
//        cookieRefresh.setSecure(false);
        cookieRefresh.setPath("/");
        cookieRefresh.setMaxAge(30); // 30s

        response.addCookie(cookie);
        response.addCookie(cookieRefresh);

        String url = String.format("%s/auth/login-success?accessToken=%s&refreshToken=%s&email=%s", frontEndUrl,
                accessToken, refreshToken,userDB.getEmail()
        );
        response.sendRedirect(url);
    }
}
