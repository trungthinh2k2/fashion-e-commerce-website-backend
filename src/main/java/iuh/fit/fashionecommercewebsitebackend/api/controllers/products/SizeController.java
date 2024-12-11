package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.SizeDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.NullDataException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.SizeMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.DeleteResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Size;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.SizeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sizes")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class SizeController {

    private final SizeMapper sizeMapper;
    private final SizeService sizeService;

    @CreateResponse
    @PostMapping
    public Response createSize(@RequestBody @Valid SizeDto sizeDto) throws DataExistsException, NullDataException {
        Size size = sizeMapper.sizeDtoToSize(sizeDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Size created successfully",
                sizeService.save(size)
        );
    }

    @FindAllResponse
    @GetMapping
    public Response getAllSizes() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all sizes successfully",
                sizeService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getSizeById(@PathVariable int id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get size by id successfully",
                sizeService.findById(id).orElseThrow(()-> new RuntimeException("Size not found"))
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response deleteSize(@PathVariable Integer id) throws DataNotFoundException {
        sizeService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Size deleted successfully "+ id
        );
    }
}