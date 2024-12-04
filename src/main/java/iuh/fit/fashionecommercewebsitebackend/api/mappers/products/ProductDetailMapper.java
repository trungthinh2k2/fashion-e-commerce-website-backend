package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDetailDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ColorService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductDetailService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductDetailMapper {

    private final ProductService productService;
    private final SizeService sizeService;
    private final ColorService colorService;
    private final ProductDetailService productDetailService;

    public ProductDetail productDetailDtoToProductDetail(ProductDetailDto productDetailDto) throws DataNotFoundException, DataExistsException {
        Product product = productService.findById(productDetailDto.getProductId())
                .orElseThrow(()->new DataNotFoundException("Product not found"));
        Size size = sizeService.findById(productDetailDto.getSizeId())
                .orElseThrow(()->new DataNotFoundException("Size not found"));
        Color color = colorService.findById(productDetailDto.getColorId())
                .orElseThrow(()->new DataNotFoundException("Color not found"));
        productDetailService.checkExist(product, color, size);
        String id = "PD_" + product.getId() + "_"+ UUID.randomUUID().toString().substring(0, 5);
        return ProductDetail.builder()
                .id(id)
                .quantity(productDetailDto.getQuantity())
                .product(product)
                .size(size)
                .color(color)
                .weight(productDetailDto.getWeight())
                .importDate(LocalDateTime.now())
                .build();
    }
}
