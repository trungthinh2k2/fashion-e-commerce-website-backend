package iuh.fit.fashionecommercewebsitebackend.services.impl;

import iuh.fit.fashionecommercewebsitebackend.models.Address;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.AddressService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address, Integer> implements AddressService {
    public AddressServiceImpl(JpaRepository<Address, Integer> repository) {
        super(repository, Address.class);
    }
}
