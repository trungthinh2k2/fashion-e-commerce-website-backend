package iuh.fit.fashionecommercewebsitebackend.api.controllers.chatbot;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.MessagesChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class MessageChatbotController {

    private final MessagesChatBotService messagesChatBotService;

    @GetMapping("/messages/{userId}/{chatbotId}")
    public List<MessageChatBot> getMessagesByUserIdAndChatbotId(@PathVariable Long userId, @PathVariable String chatbotId) {
        return messagesChatBotService.getMessagesByUserIdAndChatbotId(userId, chatbotId);
    }
}
