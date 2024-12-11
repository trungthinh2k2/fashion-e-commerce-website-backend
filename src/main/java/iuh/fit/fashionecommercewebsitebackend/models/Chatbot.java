package iuh.fit.fashionecommercewebsitebackend.models;

import jakarta.persistence.*;
import lombok.*;

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
