package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;

public interface ProductDetailService extends BaseService<ProductDetail, String>{
    ProductDetail updateQuantity(String id, Integer newQuantity);
}
