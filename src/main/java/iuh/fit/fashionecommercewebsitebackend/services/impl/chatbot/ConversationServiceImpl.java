package iuh.fit.fashionecommercewebsitebackend.services.impl.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Chatbot;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.chatbot.Conversation;
import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import iuh.fit.fashionecommercewebsitebackend.repositories.ChatbotRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.ConversationRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.MessageChatBotRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.ConversationService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.OpenAIService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.products.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatbotRepository chatbotRepository;
    private final OpenAIService openAIService;
    private final ProductService productService;

    private final MessageChatBotRepository messageChatBotRepository;

    public ConversationServiceImpl(ConversationRepository conversationRepository,
                                   ChatbotRepository chatbotRepository,
                                   OpenAIService openAIService, ProductService productService,
                                   MessageChatBotRepository messageChatBotRepository) {
        this.conversationRepository = conversationRepository;
        this.chatbotRepository = chatbotRepository;
        this.openAIService = openAIService;
        this.productService = productService;
        this.messageChatBotRepository = messageChatBotRepository;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ChatbotResponse processChatCompletion(ChatBotRequest chatBotRequest, Long userId) throws DataNotFoundException, IOException {
        Conversation conversation = conversationRepository
                .findByUserIdAndChatbotId(userId, chatBotRequest.getChatbotId())
                .orElseGet(() -> {
                    Conversation newConversation = new Conversation();
                    newConversation.setUserId(userId);
                    newConversation.setChatbotId(chatBotRequest.getChatbotId());
                    return conversationRepository.save(newConversation);
                });
        addUserMessagesToConversation(conversation, chatBotRequest.getMessages());
        String query = chatBotRequest.getMessages().get(0).getContent().replaceAll("[^a-zA-Z0-9À-ỹ ]", "").toLowerCase();
        List<String> stopWords = Files.readAllLines(Paths.get("vietnamese-stopwords.txt"), StandardCharsets.UTF_8);

        String[] tokens = query.split("\\s+");
        List<String> keywords = Arrays.stream(tokens)
                .filter(word -> !stopWords.contains(word.toLowerCase()))
                .collect(Collectors.toList());

        String refinedQuery = String.join(" ", keywords);
        // Tìm kiếm sản phẩm
        List<Product> products = productService.findByProductNameContainingIgnoreCase(refinedQuery);

        // Cập nhật câu hỏi gửi đến OpenAI và thêm thông tin sản phẩm vào đó
        assert products != null;
        ChatBotRequest fullChatRequest = prepareFullChatRequest(chatBotRequest.getModel(), conversation, products);
        // Gọi OpenAI để tạo phản hồi dựa trên lịch sử hội thoại và thông tin sản phẩm
        ChatbotResponse chatbotResponse = openAIService.getChatCompletion(fullChatRequest);

        // Lưu phản hồi cuối cùng của OpenAI vào conversation
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

    private ChatBotRequest prepareFullChatRequest(String model, Conversation conversation, List<Product> products) {
        ChatBotRequest fullChatRequest = new ChatBotRequest();
        fullChatRequest.setModel(model);

        // Lấy N tin nhắn cuối cùng (ví dụ: N = 5)
        int N = 5;
        List<MessageChatBot> recentMessages = conversation.getMessagesChatbot().stream()
                .sorted(Comparator.comparing(MessageChatBot::getTimestamp).reversed())
                .limit(N)
                .collect(Collectors.toList());

        // Đảo ngược danh sách để đúng thứ tự thời gian
        Collections.reverse(recentMessages);

        // Chuyển đổi sang danh sách Message
        List<MessageChatBot> messages = recentMessages.stream()
                .map(msg -> {
                    MessageChatBot message = new MessageChatBot();
                    message.setRole(msg.getRole());
                    message.setContent(msg.getContent());
                    return message;
                })
                .collect(Collectors.toList());

        // Thêm thông tin về sản phẩm vào yêu cầu gửi tới OpenAI
        if (!products.isEmpty()) {
            StringBuilder productInfoForOpenAI = new StringBuilder();
            for (Product product : products) {
                productInfoForOpenAI
                        .append("Sản phẩm: ").append(product.getProductName())
                        .append(", Giá: ").append(product.getPrice()).append(" VNĐ")
                        .append("\n");
            }
            MessageChatBot assistantMessage = new MessageChatBot();
            assistantMessage.setRole("assistant");
            assistantMessage.setContent("Dựa vào danh sách sản phẩm dưới đây, hãy phản hồi và tư vấn người dùng: "+ productInfoForOpenAI.toString());
            assistantMessage.setTimestamp(LocalDateTime.now()); // Hoặc lấy thời gian hiện tại

            assistantMessage.setConversation(conversation);

            messages.add(assistantMessage);
        }

        fullChatRequest.setMessages(messages);
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