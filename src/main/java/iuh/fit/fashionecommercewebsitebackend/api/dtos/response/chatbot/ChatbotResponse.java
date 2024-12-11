package iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChatbotResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
