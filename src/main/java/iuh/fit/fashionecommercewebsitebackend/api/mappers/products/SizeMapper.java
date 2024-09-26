package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.SizeDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.NullDataException;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.SizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SizeMapper {

    private final SizeService sizeService;

    public Size sizeDtoToSize(SizeDto sizeDto) throws DataExistsException, NullDataException {

        if (sizeDto.getTextSize() == null &&  sizeDto.getNumberSize() == null) {
            throw new NullDataException("text or num size must be not null");
        }
        if (sizeDto.getTextSize() != null) {
            sizeService.checkExistsTextSize(sizeDto.getTextSize());
        }
        if (sizeDto.getNumberSize() != null) {
            sizeService.checkExistsNumberSize(sizeDto.getNumberSize());
        }
        Size size = new Size();
        size.setNumberSize(sizeDto.getNumberSize());
        size.setTextSize(sizeDto.getTextSize());
        size.setSizeType(sizeDto.getSizeType());
        return size;
    }
}