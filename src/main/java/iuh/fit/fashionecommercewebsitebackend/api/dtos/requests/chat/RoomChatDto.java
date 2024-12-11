package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RoomChatDto {
    private Long adminId;
    private Long userId;
}
