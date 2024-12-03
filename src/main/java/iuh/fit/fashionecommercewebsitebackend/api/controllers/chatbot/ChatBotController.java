package iuh.fit.fashionecommercewebsitebackend.api.controllers.chatbot;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.ConversationService;
import iuh.fit.fashionecommercewebsitebackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ChatBotController {

    private final ConversationService conversationService;
    private final ValidToken validToken;

    @PostMapping("/chat-memory/{userId}")
    public ChatbotResponse getChatCompletion(@RequestBody ChatBotRequest chatBotRequest,
                                             @PathVariable long userId,
                                             HttpServletRequest request)
            throws DataNotFoundException, IOException {
        validToken.validToken(userId, request);
        return conversationService.processChatCompletion(chatBotRequest, userId);
    }

}
