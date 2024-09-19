package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProductDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProductService;
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
    public Response create(@ModelAttribute @Valid ProductDto productDto) throws DataExistsException {
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                productService.save(productDto)
        );
    }

    @FindAllResponse
    @GetMapping
    public Response getAll() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products successfully",
                productService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getById(@PathVariable String id){
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get product by id successfully",
                productService.findProductById(id)
        );
    }

    @FindAllResponse
    @GetMapping("/active")
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
    public Response deactivateProduct(@PathVariable String id) throws DataExistsException {
        productService.deactivateProduct(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Product deactivate successfully",
                null
        );
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response patchProduct(@PathVariable String id, @RequestParam Map<String, ?> data) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Product patched successfully",
                productService.updatePatch(id, data));
    }

    @GetMapping("/discount")
    public Response getProductsDiscount() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all products discount successfully",
                productService.findProductsDiscount()
        );
    }

    @GetMapping("/page-product")
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
