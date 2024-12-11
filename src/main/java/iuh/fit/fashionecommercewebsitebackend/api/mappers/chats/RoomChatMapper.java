//package iuh.fit.fashionecommercewebsitebackend.api.mappers.chats;
//
//import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.RoomChatDto;
//import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
//import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
//import iuh.fit.fashionecommercewebsitebackend.models.User;
//import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RoomChatMapper {
//
//    private final UserRepository userRepository;
//
//    public RoomChat roomChatDtoToRoomChat(RoomChatDto roomChatDto) throws DataNotFoundException {
//
//        User admin = userRepository.findById(roomChatDto.getAdminId())
//                .orElseThrow(() -> new DataNotFoundException("Admin not found"));
//        User user = userRepository.findById(roomChatDto.getUserId())
//                .orElseThrow(() -> new DataNotFoundException("User not found"));
//
//        return RoomChat.builder()
//                .user(admin)
//                .user(user)
//                .build();
//    }
//}
