package iuh.fit.fashionecommercewebsitebackend.configs.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(summary = "${api.operation.get-list.summary}",
        description = "${api.operation.get-list.description}")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
        @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}",
                content = @Content),
        @ApiResponse(responseCode = "403", description = "${api.responseCodes.forbidden.description}",
                content = @Content)
})
public  @interface FindAllResponse {
}
