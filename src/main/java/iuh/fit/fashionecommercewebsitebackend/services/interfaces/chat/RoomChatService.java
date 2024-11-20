package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

import java.util.List;

public interface RoomChatService extends BaseService<RoomChat, Long> {
    RoomChat getRoomBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists)
            throws DataNotFoundException;
    List<RoomChat> getAllRoomChat();
}
