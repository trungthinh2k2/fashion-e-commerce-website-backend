package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.models.Message;
import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.RoomChatRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    private RoomChatRepository roomChatRepository;
    private MessageRepository messageRepository;

    public MessageServiceImpl(JpaRepository<Message, String> repository) {
        super(repository, Message.class);
    }

    @Autowired
    public void setRoomChatRepository(RoomChatRepository roomChatRepository) {
        this.roomChatRepository = roomChatRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message sendMessage(MessageDto messageDto) {
        RoomChat roomChat = roomChatRepository.findById(messageDto.getRoomId())
                .orElse(null);
        if (roomChat != null) {
            // Tạo tin nhắn mới từ dữ liệu DTO
            Message message = new Message();
            message.setContent(messageDto.getContent());
            message.setSenderId(messageDto.getSenderId()); // Người gửi có thể là admin hoặc user
            message.setReceiverId(roomChat.getAdmin().getId().equals(messageDto.getSenderId())
                    ? roomChat.getUser().getId()
                    : roomChat.getAdmin().getId()); // Người nhận dựa trên người gửi
            message.setMessageTime(LocalDateTime.now());
            message.setRoomChat(roomChat); // Gán phòng chat

            // Lưu tin nhắn vào cơ sở dữ liệu
            messageRepository.save(message);
            return message; // Trả về tin nhắn để phát sóng
        }
        return null; // Hoặc xử lý trường hợp phòng chat không tồn tại
    }
}
