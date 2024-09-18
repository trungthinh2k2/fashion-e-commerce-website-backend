package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProductDetailDto {
    @NotNull(message = "Quantity must not be null")
    private Integer quantity;

    @JsonCreator
    public UpdateProductDetailDto(@JsonProperty("quantity") Integer quantity) {
        this.quantity = quantity;
    }
}
