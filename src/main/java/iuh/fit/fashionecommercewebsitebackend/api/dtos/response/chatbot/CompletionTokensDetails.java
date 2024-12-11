package iuh.fit.fashionecommercewebsitebackend.api.dtos.response.chatbot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompletionTokensDetails {
    private int reasoning_tokens;
}
