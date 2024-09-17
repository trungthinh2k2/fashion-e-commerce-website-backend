package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Category;

public interface CategoryService extends BaseService<Category, Integer>{
    Category checkExistsCategoryName(String categoryName) throws DataExistsException;
}
