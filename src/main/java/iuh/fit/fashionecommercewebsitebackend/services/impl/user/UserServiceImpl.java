package iuh.fit.fashionecommercewebsitebackend.services.impl.user;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressMapper addressMapper;


    public UserServiceImpl(JpaRepository<User, Long> repository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder, AddressMapper addressMapper) {
        super(repository, User.class);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressMapper = addressMapper;
    }

    @Override
    public User getUserByEmail(String email) throws DataNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    @Override
    public User updateUser(String email, UserUpdateDto userUpdateDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setUsername(userUpdateDto.getUsername());
        user.setPhone(userUpdateDto.getPhone());
        user.setDateOfBirth(userUpdateDto.getDateOfBirth());
        user.setGender(userUpdateDto.getGender());
        user.setAddress(addressMapper.addressDtoToAddress(userUpdateDto.getAddress()));
        user.setAvatarUrl(userUpdateDto.getAvatarUrl());
        return userRepository.save(user);
    }

    @Override
    public User updateAvatar(String email, String avatarUrl) throws DataNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        user.setAvatarUrl(avatarUrl);
        return userRepository.save(user);
    }

    @PostConstruct
    public void createAdminAccount() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setUsername("admin");
        user.setRole(Role.ROLE_ADMIN);
        user.setVerify(true);
        user.setPhone("");
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }
}
