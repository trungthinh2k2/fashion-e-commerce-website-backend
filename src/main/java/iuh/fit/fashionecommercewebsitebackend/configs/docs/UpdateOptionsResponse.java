package iuh.fit.fashionecommercewebsitebackend.configs.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(summary = "${api.operation.options-update.summary}",
        description = "${api.operation.options-update.description}")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}",
                content = @Content),
        @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "${api.responseCodes.forbidden.description}",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "${api.responseCodes.dataExists.description}",
                content = @Content)
})
public @interface UpdateOptionsResponse {

}