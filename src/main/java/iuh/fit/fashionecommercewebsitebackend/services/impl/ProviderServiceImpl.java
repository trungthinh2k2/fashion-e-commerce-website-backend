package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProviderRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl extends BaseServiceImpl<Provider, Integer> implements ProviderService {

    private ProviderRepository providerRepository;

    public ProviderServiceImpl(JpaRepository<Provider, Integer> repository) {
        super(repository, Provider.class);
    }

    @Autowired
    public void setProviderRepository(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public void checkExistsProviderName(String providerName) throws DataExistsException {
        if (providerRepository.existsByProviderName(providerName)) {
            throw new DataExistsException("Provider name already exists");
        }
    }
}
