package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyEmailDto {
    private String email;
    private String otp;
}