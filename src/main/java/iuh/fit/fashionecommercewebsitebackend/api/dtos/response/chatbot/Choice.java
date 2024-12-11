package iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot;

import iuh.fit.fashionecommercewebsitebackend.models.chatbot.MessageChatBot;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Choice {
    private int index;
    private MessageChatBot message;
    private Object logprobs;
    private String finish_reason;
}
