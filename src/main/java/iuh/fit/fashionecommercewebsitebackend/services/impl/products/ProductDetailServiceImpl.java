package iuh.fit.fashionecommercewebsitebackend.services.impl.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductDetailRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail, String> implements ProductDetailService {

    private ProductRepository productRepository;
    private ProductDetailRepository productDetailRepository;

    public ProductDetailServiceImpl(JpaRepository<ProductDetail, String> repository) {
        super(repository, ProductDetail.class
        );
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    public ProductDetail save(ProductDetail productDetail) {
        Product product = productDetail.getProduct();
        int quantity = product.getTotalQuantity() != null ? product.getTotalQuantity() : 0;
        product.setTotalQuantity(quantity + productDetail.getQuantity());
        product.setImportDate(LocalDateTime.now());
        productRepository.save(product);
        return super.save(productDetail);
    }

    @Override
    public ProductDetail updateQuantity(String id, Integer newQuantity) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found"));
        Integer oldQuantity = productDetail.getQuantity();
        int differencePD = newQuantity + oldQuantity;

        productDetail.setQuantity(differencePD);
        productDetail.setImportDate(LocalDateTime.now());
        productDetailRepository.save(productDetail);

        Product product = productDetail.getProduct();
        product.setTotalQuantity(product.getTotalQuantity() + newQuantity);
        product.setImportDate(LocalDateTime.now());
        productRepository.save(product);

        return super.save(productDetail);
    }

    @Override
    public void checkExist(Product product, Color color, Size size) throws DataExistsException {
        if(productDetailRepository.existsByProductAndColorAndSize(product, color, size))
            throw new DataExistsException("Product detail already exists");
    }
}
