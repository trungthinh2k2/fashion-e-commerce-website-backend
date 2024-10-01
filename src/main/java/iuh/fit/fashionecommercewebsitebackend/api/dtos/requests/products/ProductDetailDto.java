package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailDto {
    @NotNull(message = "Quantity must not be null")
    private Integer quantity;
    @NotNull(message = "Product ID must not be null")
    private String productId;
    @NotNull(message = "Size ID must not be null")
    private Integer sizeId;
    @NotNull(message = "Color ID must not be null")
    private Integer colorId;
    @NotNull(message = "Weight must not be null")
    private Float weight;
}
