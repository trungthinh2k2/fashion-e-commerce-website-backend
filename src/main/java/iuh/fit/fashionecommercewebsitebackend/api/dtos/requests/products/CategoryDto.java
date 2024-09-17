package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    @NotBlank(message = "Category name must be not blank")
    private String categoryName;

    @JsonCreator
    public CategoryDto(@JsonProperty("categoryName") String categoryName) {
        this.categoryName = categoryName;
    }
}
