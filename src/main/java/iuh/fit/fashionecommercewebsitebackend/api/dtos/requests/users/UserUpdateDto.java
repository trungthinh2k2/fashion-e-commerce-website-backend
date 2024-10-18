package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.AddressDto;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserUpdateDto {
    @NotBlank(message = "Username must be not blank")
    private String username;
    @NotBlank(message = "Phone must be not blank")
    @Pattern(regexp = "^(0)\\d{9}$", message = "Phone number is invalid")
    private String phone;
    private LocalDateTime dateOfBirth;
    private Gender gender;
    private AddressDto address;
    private String avatarUrl;
}
