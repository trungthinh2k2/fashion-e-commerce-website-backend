package iuh.fit.fashionecommercewebsitebackend.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_chatbots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chatbot {

    @Id
    @Column(name = "chatbot_id", length = 50, nullable = false)
    private String id;

}
