//package iuh.fit.fashionecommercewebsitebackend.api.mappers.chats;
//
//import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
//import iuh.fit.fashionecommercewebsitebackend.models.Message;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageMapper {
//
//    public Message messageDtoToMessage(MessageDto messageDto) {
//        return Message.builder()
//                .senderId(messageDto.getSenderId())
//                .receiverId(messageDto.getReceiverId())
//                .content(messageDto.getContent())
//                .build();
//    }
//}
