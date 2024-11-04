package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;

public interface ConversationService {
    ChatbotResponse processChatCompletion(ChatBotRequest chatBotRequest, Long userId
    );
}
