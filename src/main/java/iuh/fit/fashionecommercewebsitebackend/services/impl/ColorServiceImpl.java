package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.repositories.ColorRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorServiceImpl extends BaseServiceImpl<Color, Integer> implements ColorService {

    private ColorRepository colorRepository;

    public ColorServiceImpl(JpaRepository<Color, Integer> repository) {
        super(repository, Color.class);
    }

    @Autowired
    public void setColorRepository(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

        @Override
    public Color checkExistsColorName(String colorName) throws DataExistsException {
        if (colorRepository.existsByColorName(colorName)) {
            throw new DataExistsException("Color name already exists");
        }
            return null;
        }
}
