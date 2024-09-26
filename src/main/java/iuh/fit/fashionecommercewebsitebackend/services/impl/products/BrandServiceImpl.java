package iuh.fit.fashionecommercewebsitebackend.services.impl.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Brand;
import iuh.fit.fashionecommercewebsitebackend.repositories.BrandRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand, Integer> implements BrandService {

    private BrandRepository brandRepository;

    public BrandServiceImpl(JpaRepository<Brand, Integer> repository) {
        super(repository, Brand.class);
    }

    @Autowired
    public void setBrandRepository(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void checkExistsBrandName(String brandName) throws DataExistsException {
        if (brandRepository.existsByBrandName(brandName)) {
            throw new DataExistsException("Brand name already exists");
        }
    }
}
