package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot;

import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;

import java.util.List;

public interface MessagesChatBotService {
    List<MessageChatBot> getMessagesByUserIdAndChatbotId(Long userId, String chatbotId);
}
