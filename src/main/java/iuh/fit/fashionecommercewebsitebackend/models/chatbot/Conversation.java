package iuh.fit.fashionecommercewebsitebackend.models.chatbot;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import iuh.fit.fashionecommercewebsitebackend.models.ids.ChatbotUserId;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ChatbotUserId.class)
public class Conversation {

    @Id
    private Long userId;

    @Id
    private String chatbotId;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MessageChatBot> messagesChatbot = new ArrayList<>();
}
