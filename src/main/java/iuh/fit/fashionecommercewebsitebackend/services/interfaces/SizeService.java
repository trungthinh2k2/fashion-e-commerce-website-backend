package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Size;

public interface SizeService extends BaseService<Size, Integer>{
    void checkExistsTextSize(String textSize) throws DataExistsException;
    void checkExistsNumberSize(Integer numberSize) throws DataExistsException;
}
