package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.models.Message;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.SocketService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketServiceImpl implements SocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public SocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendMessageToClient(Message message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
