package iuh.fit.fashionecommercewebsitebackend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_roomchats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomchat_id", nullable = false)
    private Long id;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;
}
