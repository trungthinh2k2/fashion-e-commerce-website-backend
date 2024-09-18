package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.models.Category;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BrandService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.CategoryService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProviderService providerService;

    public Product productDtoToProduct(ProductDto productDto) {

        Brand brand = brandService.findById(productDto.getBrandId())
                .orElseThrow(() -> new DataNotFoundException("Brand not found"));

        Category category = categoryService.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        Provider provider = providerService.findById(productDto.getProviderId())
                .orElseThrow(() -> new DataNotFoundException("Provider not found"));
        return Product.builder()
                .productName(productDto.getProductName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .productStatus(Status.ACTIVE)
                .brand(brand)
                .category(category)
                .provider(provider)
                .build();
    }
}
