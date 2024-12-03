package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;

import java.io.IOException;

public interface ConversationService {
    ChatbotResponse processChatCompletion(ChatBotRequest chatBotRequest, Long userId
    ) throws DataNotFoundException, IOException;
}
