package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import iuh.fit.fashionecommercewebsitebackend.models.enums.DeliveryMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
public class ShippingDto {
    @NotNull(message = "Pick province must be not null")
    private String pickProvince;
    private String pickDistrict;
    private String province;
    private String district;
    private Double weight;
    private DeliveryMethod deliveryMethod;
}
