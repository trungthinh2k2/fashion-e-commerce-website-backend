package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import iuh.fit.fashionecommercewebsitebackend.models.enums.Scope;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
public class VoucherDto {
    @NotNull(message = "Product name must be not null")
    private String name;
    private String note;
    @NotNull(message = "Discount must not be null")
    @Range(min = 0, max = 100, message = "Discount must be between 0 and 100")
    private Integer discount;
    private VoucherType voucherType;
    private Scope scope;
    @NotNull(message = "Start date must not be null")
    @FutureOrPresent(message = "Start date must be greater than or equal to current date")
    private LocalDateTime startDate;
    @NotNull(message = "Expired date must not be null")
    @Future(message = "Expired date must be greater than current date")
    private LocalDateTime expiredDate;
    private Double maxDiscountAmount;
    private Double minOrderAmount;
    private Integer quantity;
}