package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.repositories.SizeRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SizeServiceImpl extends BaseServiceImpl<Size, Integer> implements SizeService {

    private SizeRepository sizeRepository;

    public SizeServiceImpl(JpaRepository<Size, Integer> repository) {
        super(repository, Size.class);
    }

    @Autowired
    public void setSizeRepository(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public void checkExistsTextSize(String textSize) throws DataExistsException {
        if (sizeRepository.existsByTextSize(textSize)) {
            throw new DataExistsException("Text size already exists");
        }
    }

    @Override
    public void checkExistsNumberSize(Integer numberSize) throws DataExistsException {
        if (sizeRepository.existsByNumberSize(numberSize)) {
            throw new DataExistsException("Number size already exists");
        }
    }
}
