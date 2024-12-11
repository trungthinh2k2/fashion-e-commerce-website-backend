package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.AddressDto;
import iuh.fit.fashionecommercewebsitebackend.models.enums.DeliveryMethod;
import iuh.fit.fashionecommercewebsitebackend.models.enums.PaymentMethod;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
//    @NotNull(message = "Email must be not null")
    private String email;
    private PaymentMethod paymentMethod;
    private String note;
    @Pattern(regexp = "^(0)\\d{9}$", message = "Phone number is invalid")
    private String phoneNumber;
    private String buyerName;
    private DeliveryMethod deliveryMethod;
    private Double deliveryFee;
    private AddressDto address;
    private String addressDetail;
    private List<ProductsOrderDto> productsOrderDtos;
    private List<Long> vouchers;
}
