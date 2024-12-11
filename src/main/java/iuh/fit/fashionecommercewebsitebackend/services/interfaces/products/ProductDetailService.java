package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface ProductDetailService extends BaseService<ProductDetail, String> {
    ProductDetail updateQuantity(String id, Integer newQuantity) throws Exception;
    void checkExist(Product product, Color color, Size size) throws DataExistsException;
}
