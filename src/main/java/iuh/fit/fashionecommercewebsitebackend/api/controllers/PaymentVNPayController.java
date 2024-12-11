package iuh.fit.fashionecommercewebsitebackend.api.controllers;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.PaymentVNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentVNPayController {

    private final PaymentVNPayService paymentVNPayService;

    @GetMapping("/vnp")
    public Response payment(HttpServletRequest req) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Payment successfully",
                paymentVNPayService.paymentUrl(req)
        );
    }

    @PostMapping("/payment-success")
    public Response paymentSuccess(@RequestBody Map<String, String> params, HttpServletRequest req) throws Exception {
        System.out.println("Received params: " + params);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Payment successfully",
                paymentVNPayService.paymentSuccess(params ,req)
        );
    }
}
