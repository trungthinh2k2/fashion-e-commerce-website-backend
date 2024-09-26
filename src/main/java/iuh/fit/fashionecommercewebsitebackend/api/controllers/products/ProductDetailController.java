package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDetailDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.UpdateProductDetailDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ProductDetailMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.models.ProductDetail;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/productDetails")
@RequiredArgsConstructor
@SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
public class ProductDetailController {
    private final ProductDetailService productDetailService;
    private final ProductDetailMapper productDetailMapper;

    @CreateResponse
    @PostMapping
    public Response createProductDetail(@RequestBody @Valid ProductDetailDto productDetailDto) {
        ProductDetail productDetail = productDetailMapper.productDetailDtoToProductDetail(productDetailDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Product detail created successfully",
                productDetailService.save(productDetail)
        );
    }

    @PatchMapping("/{id}")
    public ResponseSuccess<?> updatePatchProductDetail(@PathVariable String id,@RequestBody @Valid UpdateProductDetailDto updateProductDetailDto) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Product detail updated successfully",
                productDetailService.updateQuantity(id, updateProductDetailDto.getQuantity())
        );
    }
}
