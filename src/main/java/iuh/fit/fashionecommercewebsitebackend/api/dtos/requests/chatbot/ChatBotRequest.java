package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot;

import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatBotRequest {
    private String model;
    private String chatbotId;
    private List<MessageChatBot> messages;
}
