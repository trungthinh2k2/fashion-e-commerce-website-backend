package iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;

public interface OpenAIService {
    ChatbotResponse getChatCompletion(ChatBotRequest chatBotRequest);
}
