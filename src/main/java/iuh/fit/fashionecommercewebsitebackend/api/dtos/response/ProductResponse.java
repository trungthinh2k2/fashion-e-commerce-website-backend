package iuh.fit.fashionecommercewebsitebackend.api.dtos.response;

import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.models.ProductImage;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {
    private Product product;
    private List<ProductDetail> productDetail;
    private List<ProductImage> productImage;
}
