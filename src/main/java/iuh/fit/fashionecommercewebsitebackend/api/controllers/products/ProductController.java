package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import io.swagger.v3.oas.annotations.Operation;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.UpdateOptionsResponse;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @CreateResponse
    @PostMapping
    public Response create(@ModelAttribute @Valid ProductDto productDto) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                productService.save(productDto)
        );
    }

    @FindAllResponse
    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products include active and inactive products for admin")
    public Response getAll() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products successfully",
                productService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getById(@PathVariable String id) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get product by id successfully",
                productService.findProductById(id)
        );
    }

    @FindAllResponse
    @GetMapping("/active")
    @Operation(summary = "Get all products by status ACTIVE", description = "Get all products by status ACTIVE for user")
    public Response getAllProductByStatus() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products by status ACTIVE successfully",
                productService.findAllByStatus(Status.ACTIVE)
        );
    }

    @GetMapping("/top3")
    public Response getTop3Product() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get top 3 products successfully",
                productService.findTop3ByOrderByCreatedAtDesc()
        );
    }

    @PutMapping("/delete/{id}")
    @Operation(summary = "Deactivate product", description = "Deactivate product by id")
    public Response deactivateProduct(@PathVariable String id) throws DataExistsException, DataNotFoundException {
        productService.deactivateProduct(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Product deactivate successfully",
                null
        );
    }

    @UpdateOptionsResponse
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response patchProduct(@PathVariable String id, @RequestParam Map<String, ?> data) throws DataNotFoundException {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Product patched successfully",
                productService.updatePatch(id, data));
    }

//    @GetMapping("/discount")
//    public Response getProductsDiscount() {
//        return new ResponseSuccess<>(
//                HttpStatus.OK.value(),
//                "Get all products discount successfully",
//                productService.findProductsDiscount()
//        );
//    }

    @GetMapping("/page-product")
    @Operation(summary = "Get all products", description = "Get all products for user")
    public Response pageProduct(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String[] sort,
                                @RequestParam(required = false, defaultValue = "") String[] search
    ) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products successfully",
                productService.getProductsForUserRole(pageNo, pageSize, search, sort)
        );
    }

    @GetMapping("/page-product-discount")
    @Operation(summary = "Get all products discount", description = "Get all products discount for user")
    public Response pageProductDiscount(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String[] sort,
                                @RequestParam(required = false, defaultValue = "") String[] search
    ) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products discount successfully",
                productService.getProductsDiscount(pageNo, pageSize, search, sort)
        );
    }
}
