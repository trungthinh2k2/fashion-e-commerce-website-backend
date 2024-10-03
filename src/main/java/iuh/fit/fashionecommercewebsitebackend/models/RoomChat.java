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

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
