package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRegisterDto {
    @NotBlank(message = "Email must be not blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String email;
    @NotBlank(message = "Password must be not blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotBlank(message = "Username must be not blank")
    private String username;
    @NotBlank(message = "Phone must be not blank")
    @Pattern(regexp = "^(0)\\d{9}$")
    private String phone;
}
