package iuh.fit.fashionecommercewebsitebackend.services.interfaces;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;

public interface ProviderService extends BaseService<Provider, Integer>{
    void checkExistsProviderName(String providerName) throws DataExistsException;
}
