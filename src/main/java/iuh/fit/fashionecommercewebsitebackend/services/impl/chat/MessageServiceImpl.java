package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Message;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.RoomChatService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    private MessageRepository messageRepository;
    private RoomChatService roomChatService;
    private SocketService socketService;

    public MessageServiceImpl(JpaRepository<Message, String> repository) {
        super(repository, Message.class);
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Autowired
    public void setRoomChatService(RoomChatService roomChatService) {
        this.roomChatService = roomChatService;
    }

    @Autowired
    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public Message sendMessage(MessageDto messageDto) throws DataNotFoundException {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setSender(messageDto.getSender());
        message.setReceiver(messageDto.getReceiver());
        message.setContent(messageDto.getContent());
        message.setMessageTime(LocalDateTime.now());
        message.setRoomChat(roomChatService.getRoomBySenderAndReceiver(
                messageDto.getSender(), messageDto.getReceiver(), true));
        super.save(message);
        socketService.sendMessageToClient(message);
        return message;
    }

    @Override
    public List<Message> getMessagesByRoomChatId(Long roomChatId) {
        return messageRepository.findByRoomChat_Id(roomChatId);
    }
}
