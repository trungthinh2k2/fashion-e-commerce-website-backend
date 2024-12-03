package iuh.fit.fashionecommercewebsitebackend.services.impl.chatbot;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chatbot.ChatBotRequest;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot.ChatbotResponse;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chatbot.OpenAIService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenAIServiceImpl implements OpenAIService {
    @Value("${spring.ai.openai.api-key}")
    private String openaiApiKey;

    @Value("${spring.ai.openai.chat.base-url}")
    private String url;

    @Transactional(rollbackOn = Exception.class)
    public ChatbotResponse getChatCompletion(ChatBotRequest chatBotRequest) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        // Chuyển đổi danh sách Message thành định dạng yêu cầu của OpenAI
        List<Map<String, String>> messages = chatBotRequest.getMessages().stream()
                .map(msg -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("role", msg.getRole());
                    map.put("content", msg.getContent());
                    return map;
                })
                .collect(Collectors.toList());

        // Tạo body yêu cầu
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatBotRequest.getModel());
        requestBody.put("messages", messages);
        requestBody.put("temperature", 0);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ChatbotResponse> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                ChatbotResponse.class
        );

        return responseEntity.getBody();
    }
}
