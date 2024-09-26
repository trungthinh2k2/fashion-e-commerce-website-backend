package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.BrandDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.BrandMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.*;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @CreateResponse
    @PostMapping
    public Response createBrand(@Valid @RequestBody BrandDto brandDto) throws DataExistsException {
        Brand brand = brandMapper.brandDto2Brand(brandDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Brand created successfully",
                brandService.save(brand)
                );
    }

    @FindAllResponse
    @GetMapping
    public Response getAllBrands() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all brands successfully",
                brandService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getBrandById(@PathVariable int id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get brands successfully",
                brandService.findById(id).orElseThrow(()-> new DataNotFoundException("Brand not found"))
        );
    }

    @FullUpdateResponse
    @PutMapping("/{id}")
    public Response updateBrand(@PathVariable int id, @Valid @RequestBody BrandDto brandDto) throws DataExistsException {
        Brand brand = brandMapper.brandDto2Brand(brandDto);
        brand.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Brand updated successfully",
                brandService.update(id, brand)
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response deleteBrand(@PathVariable int id) {
        brandService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Brand deleted successfully with id: " + id
        );
    }
}
