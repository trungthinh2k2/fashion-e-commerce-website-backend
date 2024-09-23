package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
