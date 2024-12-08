package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProviderDto {
    @NotBlank(message = "Branch name must be not blank")
    private String providerName;
    @Pattern(regexp = "^(0)\\d{9}$", message = "Phone number is invalid")
    private String phoneNumber;
    @NotBlank(message = "Email must be not blank")
    private String email;
//    private Integer addressId;
    private String address;
}
