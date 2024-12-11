package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyDiscountOrderDto {
    private Double originalAmount;
    private Long voucherId;
}
