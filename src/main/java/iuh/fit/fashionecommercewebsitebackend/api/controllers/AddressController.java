package iuh.fit.fashionecommercewebsitebackend.api.controllers;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.AddressDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Address;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final AddressMapper addressMapper;

    @CreateResponse
    @PostMapping
    public Response create(@RequestBody @Valid AddressDto addressDto) {
        Address address = addressMapper.addressDtoToAddress(addressDto);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Address created successfully",
                addressService.save(address));
    }

    @FindAllResponse
    @GetMapping
    public Response getAllAddress() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all address successfully",
                addressService.findAll()
        );
    }

}
