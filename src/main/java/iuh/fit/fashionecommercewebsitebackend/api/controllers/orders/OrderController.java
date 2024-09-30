package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FullUpdateResponse;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
public class OrderController {

    private final OrderService orderService;

    @CreateResponse
    @PostMapping("/user/create")
    public Response createOrder(@RequestBody @Valid OrderDto orderDto) throws Exception {
        System.out.println(orderDto.getEmail());
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Order created successfully",
                orderService.save(orderDto)
        );
    }

    @FullUpdateResponse
    @Operation(summary = "Update order status to cancel", description = "Update order status to cancel")
    @PutMapping("/user/update/{id}")
    public Response updateStatusCancel(@PathVariable String id) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Order canceled successfully",
                orderService.updateStatus(id)
        );
    }

    @Operation(summary = "Get all orders", description = "Get all orders by username(email)")
    @GetMapping("/user/{email}")
    public Response getAll(@PathVariable String email) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all orders successfully",
                orderService.getAllOrdersByEmail(email)
        );
    }
}
