package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.models.Category;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.BrandService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.CategoryService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final ProviderService providerService;

    public Product productDtoToProduct(ProductDto productDto) throws DataNotFoundException {

        Brand brand = brandService.findById(productDto.getBrandId())
                .orElseThrow(() -> new DataNotFoundException("Brand not found"));

        Category category = categoryService.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        Provider provider = providerService.findById(productDto.getProviderId())
                .orElseThrow(() -> new DataNotFoundException("Provider not found"));
        return Product.builder()
                .productName(productDto.getProductName())
                .inputPrice(productDto.getInputPrice())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .productStatus(Status.ACTIVE)
                .brand(brand)
                .category(category)
                .provider(provider)
                .productNameConvert(productDto.getProductName() != null ? toLowerCaseAndRemoveAccents(productDto.getProductName()) : null)
                .build();
    }

    private String toLowerCaseAndRemoveAccents(String input) {
        if (input == null) {
            return null;
        }

        // Bước 1: Chuyển về chữ thường
        String result = input.toLowerCase();

        // Bước 2: Chuẩn hóa và loại bỏ dấu
        result = Normalizer.normalize(result, Normalizer.Form.NFD);
        result = result.replaceAll("\\p{M}", ""); // Loại bỏ dấu

        // Bước 3: Loại bỏ ký tự đặc biệt (nếu cần)
        result = result.replaceAll("[^a-z0-9\\s-]", ""); // Chỉ giữ chữ cái, số, khoảng trắng và dấu gạch ngang
        result = result.replaceAll("\\s+", " ").trim(); // Loại bỏ khoảng trắng thừa

        return result;
    }
}
