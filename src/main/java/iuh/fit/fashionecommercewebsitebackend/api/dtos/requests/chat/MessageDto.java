package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDto {
    private Long senderId;
    private String content;
    private Long roomId;
}
