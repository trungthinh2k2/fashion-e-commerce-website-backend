package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Category;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface CategoryService extends BaseService<Category, Integer> {
    Category checkExistsCategoryName(String categoryName) throws DataExistsException;
}
