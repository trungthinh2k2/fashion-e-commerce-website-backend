package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProductPriceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice, Integer> implements ProductPriceService {
    public ProductPriceServiceImpl(JpaRepository<ProductPrice, Integer> repository) {
        super(repository, ProductPrice.class);
    }
}
