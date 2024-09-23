package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ProviderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ProviderMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.*;
import iuh.fit.fashionecommercewebsitebackend.models.Provider;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/providers")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class ProviderController {

    private final ProviderService providerService;
    private final ProviderMapper providerMapper;

    @CreateResponse
    @PostMapping
    public Response create(@Valid @RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.providerDtoToProvider(providerDto);
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
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get provider by id successfully",
                providerService.findById(id).orElseThrow(()-> new RuntimeException("Provider not found"))
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response deleteProvider(@PathVariable Integer id) {
        providerService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Provider deleted successfully "+ id
        );
    }

    @FullUpdateResponse
    @PutMapping("/{id}")
    public Response updateProvider(@PathVariable int id, @Valid @RequestBody ProviderDto providerDto) {
        Provider provider = providerMapper.providerDtoToProvider(providerDto);
        provider.setId(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Provider updated successfully",
                providerService.update(id, provider)
        );
    }

    @PatchMapping("/{id}")
    public Response updateProviderPartially(@PathVariable int id, @RequestBody Map<String, ?> data) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Provider updated patch successfully",
                providerService.updatePatch(id, data)
        );
    }
}
