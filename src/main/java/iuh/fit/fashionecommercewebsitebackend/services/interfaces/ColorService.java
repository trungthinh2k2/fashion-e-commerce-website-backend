package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;

public interface ColorService extends BaseService<Color, Integer>{
    Color checkExistsColorName(String colorName) throws DataExistsException;
}
