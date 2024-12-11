package iuh.fit.fashionecommercewebsitebackend.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUserResponse {
    private Product product;
    private Integer discount;
    private Double discountedPrice;
    private Double discountedAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;
    private  Double priceFinal;
    private String status;
}