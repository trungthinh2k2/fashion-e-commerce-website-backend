package iuh.fit.fashionecommercewebsitebackend.services.impl.products;

import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductPriceRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductPriceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductPriceServiceImpl extends BaseServiceImpl<ProductPrice, Integer> implements ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public ProductPriceServiceImpl(JpaRepository<ProductPrice, Integer> repository, ProductPriceRepository productPriceRepository) {
        super(repository, ProductPrice.class);
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    public List<ProductPrice> findAllByProductId(String productId) {
        return productPriceRepository.findAllByProductId(productId);
    }
}
