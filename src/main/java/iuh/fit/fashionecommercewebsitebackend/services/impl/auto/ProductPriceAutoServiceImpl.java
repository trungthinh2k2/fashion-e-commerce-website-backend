package iuh.fit.fashionecommercewebsitebackend.services.impl.auto;

import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductPriceRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.auto.ProductPriceAutoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductPriceAutoServiceImpl implements ProductPriceAutoService {

    private final ProductRepository productRepository;
    private final ProductPriceRepository productPriceRepository;

    public ProductPriceAutoServiceImpl(ProductRepository productRepository,
                                       ProductPriceRepository productPriceRepository) {
        this.productRepository = productRepository;
        this.productPriceRepository = productPriceRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndCreateProductPrice() {
        System.out.println("Running at: " + LocalDateTime.now());
        LocalDateTime twoMonthsAgo = LocalDateTime.now().minusMonths(2);

        List<Product> products = productRepository.findByImportDateBefore(twoMonthsAgo);

        for (Product product : products) {
            boolean alreadyProcessed = productPriceRepository.existsByProductId(product.getId());
            if (!alreadyProcessed) {
                double discount = (product.getPrice() - product.getInputPrice()) / product.getPrice() * 100;
                double discountedPrice = product.getPrice() * (int)discount / 100;
                double discountedAmount = product.getPrice() - discountedPrice;

                ProductPrice productPrice = new ProductPrice();
                productPrice.setProduct(product);
                productPrice.setDiscount((int) discount);
                productPrice.setDiscountedPrice(discountedPrice);
                productPrice.setDiscountedAmount(discountedAmount);
                productPrice.setIssueDate(LocalDateTime.now());
                productPrice.setExpiredDate(LocalDateTime.now().plusMonths(2));
                String note = String.format("Giảm %.0f%% cho sản phẩm vì sản phẩm đã nhập kho quá 2 tháng.", discount);
                productPrice.setNote(note);

                productPriceRepository.save(productPrice);
            }
        }
    }
}
