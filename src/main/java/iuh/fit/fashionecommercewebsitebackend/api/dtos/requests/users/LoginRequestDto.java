package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    private String email;
    private String password;
}
