package iuh.fit.fashionecommercewebsitebackend.models.chatbot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_messages_chatbot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageChatBot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumns({
            @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            @JoinColumn(name = "chatbot_id", referencedColumnName = "chatbotId")
    })
    @JsonBackReference
    private Conversation conversation;

    @Column(name = "role", nullable = false)
    private String role; // "user" hoặc "assistant"

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    // Thời gian gửi tin nhắn
    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp;

}
