package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProviderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ProviderMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @CreateResponse
    @PostMapping
    public Response create(@Valid @RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.ProviderDtoToProvider(providerDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Provider created successfully",
                providerService.save(provider)
        );
    }

    @FindAllResponse
    @GetMapping
    public Response getAll() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all providers successfully",
                providerService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getProviderById(@PathVariable int id) {
        Provider provider = providerService.findById(id).orElseThrow(()->
                new RuntimeException("Provider not found"));
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get provider by id successfully",
                providerService.findById(id).orElseThrow(()-> new RuntimeException("Provider not found"))
        );
    }
}
