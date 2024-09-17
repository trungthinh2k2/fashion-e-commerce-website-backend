package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDto {
    @NotNull(message = "Product name must be not null")
    private String productName;
    @NotNull(message = "Price must be not null")
    private Double price;
    @NotNull(message = "Description must be not null")
    private String description;
    private Integer thumbnail;
    private Integer brandId;
    private Integer categoryId;
    private Integer providerId;

    private List<MultipartFile> images;
}
