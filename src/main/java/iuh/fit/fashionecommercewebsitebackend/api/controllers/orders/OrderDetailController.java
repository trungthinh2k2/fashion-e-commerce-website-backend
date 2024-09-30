package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class OrderDetailController {

    private final OrderService orderService;

    @FindResponse
    @GetMapping("/{OrderId}")
    public Response getOrderDetailByOrderId(@PathVariable String OrderId) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get order details successfully",
                orderService.getOrderDetailsByOrderId(OrderId)
        );
    }
}
