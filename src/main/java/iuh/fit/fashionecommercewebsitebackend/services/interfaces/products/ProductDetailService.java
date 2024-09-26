package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface ProductDetailService extends BaseService<ProductDetail, String> {
    ProductDetail updateQuantity(String id, Integer newQuantity);
}
