package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

import java.util.List;

public interface ProductPriceService extends BaseService<ProductPrice, Integer> {
    List<ProductPrice> findAllByProductId(String productId);
}
