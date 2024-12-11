package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat;

import iuh.fit.fashionecommercewebsitebackend.models.Message;

public interface SocketService {
    void sendMessageToClient(Message message);
}
