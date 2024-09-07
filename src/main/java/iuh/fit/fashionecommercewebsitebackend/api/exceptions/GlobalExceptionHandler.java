package iuh.fit.fashionecommercewebsitebackend.api.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, "Data not found " + getRootCauseMessage(ex), ex));
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<Object> handleDataExistsException(DataExistsException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, "Data exists " +  getRootCauseMessage(ex), ex));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, getRootCauseMessage(ex), ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    private String getRootCauseMessage(Exception e) {
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        return rootCause == null ? e.getMessage() : rootCause.getMessage();
    }

}
