package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BrandDto {

    @NotBlank(message = "Brand name must be not blank")
    private String brandName;

    @JsonCreator
    public BrandDto(@JsonProperty("brandName") String brandName) {
        this.brandName = brandName;
    }
}
