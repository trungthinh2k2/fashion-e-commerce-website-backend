package iuh.fit.fashionecommercewebsitebackend.api.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ResponseError implements Response {
    private int status;
    private List<String> errors;

    public ResponseError(int status, List<String> errors) {
        this.status = status;
        this.errors = errors;
    }
}
