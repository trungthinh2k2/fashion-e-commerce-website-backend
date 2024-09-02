package iuh.fit.fashionecommercewebsitebackend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message extends BaseModel{

        @Id
        @Column(name = "message_id", nullable = false)
        private String id;

        @Column(name = "sender_id")
        private String senderId;

        @Column(name = "receiver_id")
        private String receiverId;

        @Column(name = "content")
        private String content;

        @Column(name = "message_time")
        private LocalDateTime messageTime;

        @ManyToOne
        @JoinColumn(name = "roomchat_id", nullable = false)
        private RoomChat roomChat;
}
