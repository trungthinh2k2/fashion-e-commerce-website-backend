package iuh.fit.fashionecommercewebsitebackend.services.impl.user;

import iuh.fit.fashionecommercewebsitebackend.models.CustomUserDetails;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}
