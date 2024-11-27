package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.models.Message;
import iuh.fit.fashionecommercewebsitebackend.models.enums.MessageType;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.RoomChatService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.SocketService;
import iuh.fit.fashionecommercewebsitebackend.utils.S3Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, String> implements MessageService {

    private final Set<String> IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "gif", "bmp"));
    private final Set<String> VIDEO_EXTENSIONS = new HashSet<>(Arrays.asList("mp4", "avi", "mov", "mkv", "webm"));
    private MessageRepository messageRepository;
    private RoomChatService roomChatService;
    private SocketService socketService;
    private S3Upload s3Upload;

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

    @Autowired
    public void setS3Upload(S3Upload s3Upload) {
        this.s3Upload = s3Upload;
    }

    @Override
    public Message sendMessage(MessageDto messageDto) throws Exception {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setSender(messageDto.getSender());
        message.setReceiver(messageDto.getReceiver());
        message.setContent(messageDto.getContent());
        message.setMessageType(MessageType.TEXT);
        message.setMessageTime(LocalDateTime.now());
        message.setRoomChat(roomChatService.getRoomBySenderAndReceiver(
                messageDto.getSender(), messageDto.getReceiver(), true));
        if (messageDto.getMediaPath() != null) {
            message.setMessageType(getMessageType(messageDto.getMediaPath()));
            message.setPath(s3Upload.uploadFile(messageDto.getMediaPath()));
        }
        super.save(message);
        socketService.sendMessageToClient(message);
        return message;
    }

    private MessageType getMessageType(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename();
        if (filename == null) {
            return MessageType.TEXT;
        }
        String extension = getFileExtension(filename);
        if(IMAGE_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.IMAGE;
        }
        if(VIDEO_EXTENSIONS.contains(extension.toLowerCase())) {
            return MessageType.VIDEO;
        }
        throw new IllegalArgumentException("only allow sending image or video");
    }

    private String getFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex >= 0 && lastDotIndex < filename.length() - 1) {
            return filename.substring(lastDotIndex + 1);
        }
        return "";
    }

    @Override
    public List<Message> getMessagesByRoomChatId(Long roomChatId) {
        return messageRepository.findByRoomChat_Id(roomChatId);
    }

    @Override
    public List<Message> getMessagesByRoomId(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
