package iuh.fit.fashionecommercewebsitebackend.api.exceptions;

public class DataExistsException extends RuntimeException{
    public DataExistsException(String message) {
        super(message);
    }
}
