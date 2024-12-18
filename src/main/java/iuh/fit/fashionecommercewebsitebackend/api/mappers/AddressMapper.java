package iuh.fit.fashionecommercewebsitebackend.api.mappers;


import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.AddressDto;
import iuh.fit.fashionecommercewebsitebackend.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public Address addressDtoToAddress(AddressDto addressDto) {
        return Address.builder()
                .street(addressDto.getStreet())
                .ward(addressDto.getWard())
                .district(addressDto.getDistrict())
                .city(addressDto.getCity())
                .build();
    }
}

