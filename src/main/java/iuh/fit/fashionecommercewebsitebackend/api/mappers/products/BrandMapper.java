package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.BrandDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandMapper {
    private final BrandService brandService;

    public Brand brandDto2Brand(BrandDto brandDto) throws DataExistsException {
        brandService.checkExistsBrandName(brandDto.getBrandName());
        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());
        return brand;
    }
}
