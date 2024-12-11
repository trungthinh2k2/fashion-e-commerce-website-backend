package iuh.fit.fashionecommercewebsitebackend.api.mappers.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users.UserDto;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final AddressMapper addressMapper;

    public User userDtoToUser(UserDto userDto) {
        return User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .username(userDto.getUsername())
                .phone(userDto.getPhone())
                .gender(userDto.getGender())
                .role(userDto.getRole())
                .dateOfBirth(userDto.getDateOfBirth())
                .address(addressMapper.addressDtoToAddress(userDto.getAddress()))
                .build();
    }


}
