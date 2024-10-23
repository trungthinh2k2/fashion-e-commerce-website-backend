package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ShippingDto;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.ShippingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Value("${ghtk.api.url}")
    private String apiUrl;

    @Value("${ghtk.api.token}")
    private String apiToken;

    public double calculateShippingFee(String pickProvince, String pickDistrict, String province, String district, double weight, String deliveryMethod) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Headers với API Token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", apiToken);

        // Request body theo cấu trúc yêu cầu của GHTK
        String requestJson = "{"
                + "\"pick_province\": \"" + pickProvince + "\","
                + "\"pick_district\": \"" + pickDistrict + "\","
                + "\"province\": \"" + province + "\","
                + "\"district\": \"" + district + "\","
                + "\"weight\": " + weight + ","
                + "\"transport\": \"" + deliveryMethod + "\""
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        try {
            // Log request details
            System.out.println("Request URL: " + apiUrl + "/services/shipment/fee");
            System.out.println("Request Headers: " + headers);
            System.out.println("Request Body: " + requestJson);

            // Gửi yêu cầu POST đến GHTK API để tính phí vận chuyển
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/services/shipment/fee", entity, String.class);

            // Log response details
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            // Parse response JSON từ API để lấy phí vận chuyển (shipping fee)
            String responseBody = response.getBody();
            return extractShippingFeeFromResponse(responseBody);

        } catch (Exception e) {
            throw new Exception("Error calculating shipping fee: " + e.getMessage());
        }
    }

    @Override
    public double calculateShippingFee(ShippingDto shippingDto) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // Headers với API Token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Token", apiToken);

        // Request body theo cấu trúc yêu cầu của GHTK
        String requestJson = "{"
                + "\"pick_province\": \"" + shippingDto.getPickProvince() + "\","
                + "\"pick_district\": \"" + shippingDto.getPickDistrict() + "\","
                + "\"province\": \"" + shippingDto.getProvince() + "\","
                + "\"district\": \"" + shippingDto.getDistrict() + "\","
                + "\"weight\": " + shippingDto.getWeight() + ","
                + "\"transport\": \"" + shippingDto.getDeliveryMethod().getTransportType() + "\""
                + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        try {
            // Log request details
            System.out.println("Request URL: " + apiUrl + "/services/shipment/fee");
            System.out.println("Request Headers: " + headers);
            System.out.println("Request Body: " + requestJson);

            // Gửi yêu cầu POST đến GHTK API để tính phí vận chuyển
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl + "/services/shipment/fee", entity, String.class);

            // Log response details
            System.out.println("Response Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            // Parse response JSON từ API để lấy phí vận chuyển (shipping fee)
            String responseBody = response.getBody();
            return extractShippingFeeFromResponse(responseBody);

        } catch (Exception e) {
            throw new Exception("Error calculating shipping fee: " + e.getMessage());
        }
    }

    private double extractShippingFeeFromResponse(String responseBody) throws Exception {
        // Sử dụng Jackson ObjectMapper để parse JSON
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse response body thành JSON Node
        JsonNode jsonNode = objectMapper.readTree(responseBody);

//         Kiểm tra phản hồi có success hay không
        boolean success = jsonNode.path("success").asBoolean();
        if (!success) {
            throw new Exception("API response indicates failure.");
        }

        // Tìm trường "fee" và sau đó là "fee"
        JsonNode feeNode = jsonNode.path("fee");
        if (feeNode.isMissingNode()) {
            throw new Exception("Fee information is missing in the response");
        }

        // Lấy giá trị phí vận chuyển từ "fee"
        double shippingFee = feeNode.path("fee").asDouble();

        // Nếu muốn tính thêm phụ phí, có thể duyệt qua danh sách "extFees"
        double totalExtraFees = 0;
        JsonNode extFeesNode = feeNode.path("extFees");
        if (extFeesNode.isArray()) {
            for (JsonNode extFee : extFeesNode) {
                totalExtraFees += extFee.path("amount").asDouble();
            }
        }

        // Tổng phí vận chuyển bao gồm cả phụ phí (nếu có)
        return shippingFee + totalExtraFees;
    }
}
