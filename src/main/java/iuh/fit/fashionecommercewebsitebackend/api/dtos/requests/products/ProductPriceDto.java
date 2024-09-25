package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ProductPriceDto {
    @NotNull(message = "Discount must not be null")
    @Range(min = 0, max = 100, message = "Discount must be between 0 and 100")
    private Integer discount;
    @NotNull(message = "Issue date must not be null")
    @FutureOrPresent(message = "Issue date must be greater than or equal to current date")
    private LocalDateTime issueDate;
    @NotNull(message = "Expired date must not be null")
    @Future(message = "Expired date must be greater than current date")
    private LocalDateTime expiredDate;
    @NotNull(message = "Note must not be null")
    private String note;
    @NotNull(message = "Product id must not be null")
    private String productId;
}
