package iuh.fit.fashionecommercewebsitebackend.api.controllers.products;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.products.ColorDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataExistsException;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.products.ColorMapper;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.DeleteResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindAllResponse;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.FindResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ColorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/colors")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class ColorController {

    private final ColorMapper colorMapper;
    private final ColorService colorService;

    @CreateResponse
    @PostMapping
    public Response createColor(@RequestBody @Valid ColorDto colorDto) throws DataExistsException {
        Color color = colorMapper.colorDtoToColor(colorDto);
        return new ResponseSuccess<>(
                HttpStatus.CREATED.value(),
                "Color created successfully",
                colorService.save(color)
        );
    }

    @FindAllResponse
    @GetMapping
    public Response getAllColors() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all colors successfully",
                colorService.findAll()
        );
    }

    @FindResponse
    @GetMapping("/{id}")
    public Response getColorById(@PathVariable int id) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get color by id successfully",
                colorService.findById(id).orElseThrow(()-> new RuntimeException("Color not found"))
        );
    }

    @DeleteResponse
    @DeleteMapping("/{id}")
    public Response deleteColor(@PathVariable Integer id) throws DataNotFoundException {
        colorService.deleteById(id);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Color deleted successfully "+ id
        );
    }
}
