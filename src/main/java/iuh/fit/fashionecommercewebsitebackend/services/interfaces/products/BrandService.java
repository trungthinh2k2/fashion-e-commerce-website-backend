package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface BrandService extends BaseService<Brand, Integer> {
    void checkExistsBrandName(String brandName) throws DataExistsException;
}
