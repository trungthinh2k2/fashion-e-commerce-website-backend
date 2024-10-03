package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.models.Message;

public interface MessageService {
    Message sendMessage(MessageDto messageDto);
}
