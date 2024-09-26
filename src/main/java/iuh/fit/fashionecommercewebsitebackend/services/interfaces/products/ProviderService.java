package iuh.fit.fashionecommercewebsitebackend.services.interfaces.products;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

public interface ProviderService extends BaseService<Provider, Integer> {
    void checkExistsProviderName(String providerName) throws DataExistsException;
}
