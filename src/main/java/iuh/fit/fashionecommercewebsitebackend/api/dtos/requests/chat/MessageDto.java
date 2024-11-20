package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private String sender;
    private String receiver;
    private String content;
}
