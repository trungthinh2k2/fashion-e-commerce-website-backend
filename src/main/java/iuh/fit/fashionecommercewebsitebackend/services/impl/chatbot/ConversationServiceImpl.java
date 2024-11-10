package iuh.fit.fashionecommercewebsitebackend.services.impl.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;
import iuh.fit.fashionecommercewebsitebackend.models.Chatbot;
import iuh.fit.fashionecommercewebsitebackend.models.chatbot.Conversation;
import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import iuh.fit.fashionecommercewebsitebackend.repositories.ChatbotRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ConversationRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageChatBotRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.ConversationService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.OpenAIService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatbotRepository chatbotRepository;
    private final OpenAIService openAIService;

    private final MessageChatBotRepository messageChatBotRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   ChatbotRepository chatbotRepository,
                                   OpenAIService openAIService, MessageChatBotRepository messageChatBotRepository) {
        this.conversationRepository = conversationRepository;
        this.chatbotRepository = chatbotRepository;
        this.openAIService = openAIService;
        this.messageChatBotRepository = messageChatBotRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ChatbotResponse processChatCompletion(ChatBotRequest chatBotRequest, Long userId) {
        Conversation conversation = conversationRepository
                .findByUserIdAndChatbotId(userId, chatBotRequest.getChatbotId())
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setUserId(userId);
                    newConversation.setChatbotId(chatBotRequest.getChatbotId());
                    return conversationRepository.save(newConversation);
                });
        addUserMessagesToConversation(conversation, chatBotRequest.getMessages());
        ChatBotRequest fullChatRequest = prepareFullChatRequest(chatBotRequest.getModel(), conversation);

        ChatbotResponse chatbotResponse = openAIService.getChatCompletion(fullChatRequest);

        addAssistantMessageToConversation(conversation, chatbotResponse);

        conversationRepository.save(conversation);
        return chatbotResponse;
    }

    private void addUserMessagesToConversation(Conversation conversation, List<MessageChatBot> userMessages) {
        for (MessageChatBot userMessage : userMessages) {
            userMessage.setRole("user");
            userMessage.setConversation(conversation);
            userMessage.setTimestamp(LocalDateTime.now());
            messageChatBotRepository.save(userMessage);
            conversation.getMessagesChatbot().add(userMessage);
        }
    }

    private ChatBotRequest prepareFullChatRequest(String model, Conversation conversation) {
        ChatBotRequest fullChatRequest = new ChatBotRequest();
        fullChatRequest.setModel(model);
        fullChatRequest.setMessages(conversation.getMessagesChatbot().stream()
                .map(msg -> {
                    MessageChatBot message = new MessageChatBot();
                    message.setRole(msg.getRole());
                    message.setContent(msg.getContent());
                    return message;
                })
                .collect(Collectors.toList()));
        return fullChatRequest;
    }

    private void addAssistantMessageToConversation(Conversation conversation, ChatbotResponse chatbotResponse) {
        MessageChatBot assistantMessage = chatbotResponse.getChoices().get(0).getMessage();
        assistantMessage.setRole("assistant");
        assistantMessage.setConversation(conversation);
        assistantMessage.setTimestamp(LocalDateTime.now());
        messageChatBotRepository.save(assistantMessage);
        conversation.getMessagesChatbot().add(assistantMessage);
    }

    @PostConstruct
    public void createChatbot() {
        Chatbot chatbot = new Chatbot();
        chatbot.setId("chatbot-AI");
        if (!chatbotRepository.existsById(chatbot.getId())) {
            chatbotRepository.save(chatbot);
        }
    }
}