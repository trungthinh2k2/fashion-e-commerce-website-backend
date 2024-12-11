package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.AddressDto;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Gender;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDto {
    @Email
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email is invalid")
    private String email;
    @NotBlank(message = "Password must be not blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Username must be not blank")
    private String username;
    @NotBlank(message = "Phone must be not blank")
    @Pattern(regexp = "^(0)\\d{9}$", message = "Phone number is invalid")
    private String phone;
    private LocalDateTime dateOfBirth;
    private Gender gender;
    private Role role;
    private AddressDto address;
}
