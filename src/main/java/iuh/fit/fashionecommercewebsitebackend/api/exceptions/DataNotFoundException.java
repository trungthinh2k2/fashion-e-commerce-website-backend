package iuh.fit.fashionecommercewebsitebackend.api.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}