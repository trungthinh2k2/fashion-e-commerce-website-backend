package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.models.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(MessageDto messageDto) throws Exception;
    List<Message> getMessagesByRoomChatId(Long roomChatId);

    List<Message> getMessagesByRoomId(String roomId);
}
