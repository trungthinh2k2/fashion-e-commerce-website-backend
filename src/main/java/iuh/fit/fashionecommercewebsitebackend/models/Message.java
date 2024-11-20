package iuh.fit.fashionecommercewebsitebackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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

        @Column(name = "sender")
        private String sender;

        @Column(name = "receiver")
        private String receiver;

        @Column(name = "content")
        private String content;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
        @Column(name = "message_time")
        private LocalDateTime messageTime;

        @ManyToOne
        @JoinColumn(name = "roomchat_id", nullable = false)
        private RoomChat roomChat;
}
