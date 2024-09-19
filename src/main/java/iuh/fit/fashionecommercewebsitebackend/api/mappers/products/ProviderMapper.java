package iuh.fit.fashionecommercewebsitebackend.api.mappers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProviderDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Address;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProviderMapper {

    private final AddressService addressService;

    public Provider providerDtoToProvider(ProviderDto providerDto) {

        Address address = addressService.findById(providerDto.getAddressId())
                .orElseThrow(() -> new DataNotFoundException("Brand not found"));

        return Provider.builder()
                .providerName(providerDto.getProviderName())
                .phoneNumber(providerDto.getPhoneNumber())
                .email(providerDto.getEmail())
                .address(address)
                .build();
    }

}