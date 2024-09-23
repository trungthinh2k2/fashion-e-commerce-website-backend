package iuh.fit.fashionecommercewebsitebackend.api.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends Throwable {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        return new ApiError(HttpStatus.BAD_REQUEST.value(), errors);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public ApiError handleDataNotFoundException(DataNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ApiError handleExpiredJwtException(ExpiredJwtException ex) {
        return new ApiError(HttpStatus.UNAUTHORIZED.value(), List.of(ex.getMessage()));
    }

//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(ForbiddenException.class)
//    public ApiError handleForbiddenException(ForbiddenException ex) {
//        return new ApiError(HttpStatus.FORBIDDEN.value(), List.of(ex.getMessage()));
//    }


}
