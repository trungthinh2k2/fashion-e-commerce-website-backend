package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
import iuh.fit.fashionecommercewebsitebackend.repositories.RoomChatRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.RoomChatService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomChatServiceImpl extends BaseServiceImpl<RoomChat, Long> implements RoomChatService {

    private final RoomChatRepository roomChatRepository;
    private final UserRepository userRepository;

    public RoomChatServiceImpl(JpaRepository<RoomChat, Long> repository,
                               RoomChatRepository roomChatRepository,
                               UserRepository userRepository) {
        super(repository, RoomChat.class);
        this.roomChatRepository = roomChatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RoomChat getRoomBySenderAndReceiver(String sender, String receiver, boolean createIfNotExists) throws DataNotFoundException {
        validateUsers(sender, receiver);

        Optional<RoomChat> optional = roomChatRepository.findBySenderAndReceiver(sender, receiver);
        if (optional.isEmpty()) {
            optional = roomChatRepository.findBySenderAndReceiver(receiver, sender);
        }

        if (optional.isEmpty()) {
            if (!createIfNotExists) {
                throw new DataNotFoundException("Room not found");
            }
            String roomId = concatRoomId(sender, receiver);
            return saveRoom(roomId, sender, receiver);
        } else {
            return optional.get();
        }
    }

    @Override
    public List<RoomChat> getAllRoomChat() {
        return roomChatRepository.findAll();
    }


    private void validateUsers(String sender, String receiver) throws DataNotFoundException {
        if (!userRepository.existsByEmail(sender)) {
            throw new DataNotFoundException("Sender not found");
        }
        if (!userRepository.existsByEmail(receiver)) {
            throw new DataNotFoundException("Receiver not found");
        }
    }

    private String concatRoomId(String sender, String receiver) {
        return sender.compareTo(receiver) < 0 ? sender + "_" + receiver : receiver + "_" + sender;
    }


    private RoomChat saveRoom(String roomId, String sender, String receiver) {
        RoomChat roomChat = new RoomChat();
        roomChat.setRoomId(roomId);
        roomChat.setSender(sender);
        roomChat.setReceiver(receiver);
        return roomChatRepository.save(roomChat);
    }

}
