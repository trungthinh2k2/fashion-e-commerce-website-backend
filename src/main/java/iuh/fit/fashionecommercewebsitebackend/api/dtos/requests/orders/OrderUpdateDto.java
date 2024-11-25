package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateDto {
    @NotNull(message = "Order status is not null")
    private OrderStatus orderStatus;


    @JsonCreator
    public OrderUpdateDto(@JsonProperty("orderStatus") OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
