package iuh.fit.fashionecommercewebsitebackend.services.impl.chatbot;

import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageChatBotRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.MessagesChatBotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesChatbotServiceImpl implements MessagesChatBotService {

    private final MessageChatBotRepository messageChatBotRepository;

    public MessagesChatbotServiceImpl(MessageChatBotRepository messageChatBotRepository) {
        this.messageChatBotRepository = messageChatBotRepository;
    }

    @Override
    public List<MessageChatBot> getMessagesByUserIdAndChatbotId(Long userId, String chatbotId) {
        return messageChatBotRepository.findByConversation_UserIdAndConversation_ChatbotIdOrderByTimestampAsc(userId, chatbotId);
    }
}
