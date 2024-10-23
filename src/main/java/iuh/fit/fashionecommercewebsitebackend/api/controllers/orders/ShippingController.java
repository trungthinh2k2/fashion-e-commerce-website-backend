package iuh.fit.fashionecommercewebsitebackend.api.controllers.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ShippingDto;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PostMapping("/fee")
    public double calculateShippingFee(@RequestBody ShippingDto shippingDto) throws Exception {
        return shippingService.calculateShippingFee(shippingDto);
    }
}
