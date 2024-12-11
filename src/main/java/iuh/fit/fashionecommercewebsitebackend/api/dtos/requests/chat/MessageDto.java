package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MessageDto {
    private String sender;
    private String receiver;
    private String content;
    private MultipartFile mediaPath;
}
