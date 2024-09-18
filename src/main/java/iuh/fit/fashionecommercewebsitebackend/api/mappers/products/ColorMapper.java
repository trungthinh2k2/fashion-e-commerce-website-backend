package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ColorDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColorMapper {

    private final ColorService colorService;

    public Color colorDtoToColor(ColorDto colorDto) throws DataExistsException {
        colorService.checkExistsColorName(colorDto.getColorName());
        Color color = new Color();
        color.setColorName(colorDto.getColorName());

        return color;
    }

}
