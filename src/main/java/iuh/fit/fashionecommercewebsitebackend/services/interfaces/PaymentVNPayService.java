package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface PaymentVNPayService {
    String paymentUrl(HttpServletRequest req) throws UnsupportedEncodingException, ServletException;
    String paymentSuccess(Map<String, String> params, HttpServletRequest req) throws Exception;
}
