package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplyDiscountShipDto {
    private Double deliveryFee;
    private Double originalAmount;
    private Long voucherId;
}
