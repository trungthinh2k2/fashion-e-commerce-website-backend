package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface ColorService extends BaseService<Color, Integer> {
    Color checkExistsColorName(String colorName) throws DataExistsException;
}
