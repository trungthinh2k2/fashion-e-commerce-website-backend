package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressDto {
    @NotBlank(message = "Street must be not blank")
    private String street;
    @NotBlank(message = "District must be not blank")
    private String district;
    @NotBlank(message = "City must be not blank")
    private String city;
}