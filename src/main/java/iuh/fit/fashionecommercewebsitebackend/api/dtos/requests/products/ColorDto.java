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
public class ColorDto {

    @NotBlank(message = "Color name must be not blank")
    private String colorName;

    @JsonCreator
    public ColorDto(@JsonProperty("colorName") String colorName) {
        this.colorName = colorName;
    }
}
