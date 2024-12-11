package iuh.fit.fashionecommercewebsitebackend.configs.docs;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "${api.responseCodes.forbidden.description}",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "${api.responseCodes.internalServerError.description}",
                content = @Content)
})
public @interface GeneralResponse {
}
