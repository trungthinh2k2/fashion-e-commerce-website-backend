package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;

public interface BrandService extends BaseService<Brand, Integer>{
    void checkExistsBrandName(String brandName) throws DataExistsException;
}
