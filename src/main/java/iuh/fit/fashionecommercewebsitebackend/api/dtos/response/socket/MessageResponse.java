package iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse<T> {
    private T data;
    private String type;
}
