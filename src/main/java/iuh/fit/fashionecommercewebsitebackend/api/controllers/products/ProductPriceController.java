package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductPriceDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ProductPriceMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.models.ProductPrice;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProductPriceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/productPrices")
@RequiredArgsConstructor
@Validated
public class ProductPriceController {

    private final ProductPriceMapper productPriceMapper;
    private final ProductPriceService productPriceService;

    @CreateResponse
    @PostMapping
    public Response createProductPrice(@RequestBody @Valid ProductPriceDto productPriceDto) throws DataNotFoundException {

        ProductPrice productPrice = productPriceMapper.productPriceDto2ProductPrice(productPriceDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Product price created successfully",
                productPriceService.save(productPrice)
        );
    }
}