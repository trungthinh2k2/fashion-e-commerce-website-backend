package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageChatBotRepository extends JpaRepository<MessageChatBot, Long> {
    List<MessageChatBot> findByConversation_UserIdAndConversation_ChatbotIdOrderByTimestampAsc(Long userId, String chatbotId);
}