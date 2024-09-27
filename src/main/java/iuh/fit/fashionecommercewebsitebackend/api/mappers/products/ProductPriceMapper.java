package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductPriceDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPriceMapper {

    private final ProductService productService;

    public ProductPrice productPriceDto2ProductPrice(ProductPriceDto productPriceDto) throws DataNotFoundException {

        Product product = productService.findById(productPriceDto.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Product not found"));

        double discountedPrice = product.getPrice() * productPriceDto.getDiscount() / 100;
        double discountedAmount = product.getPrice() - discountedPrice;

        return ProductPrice.builder()
                .product(product)
                .discount(productPriceDto.getDiscount())
                .discountedPrice(discountedPrice)
                .discountedAmount(discountedAmount)
                .issueDate(productPriceDto.getIssueDate())
                .expiredDate(productPriceDto.getExpiredDate())
                .note(productPriceDto.getNote())
                .build();
    }
}