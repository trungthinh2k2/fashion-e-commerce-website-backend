package iuh.fit.fashionecommercewebsitebackend.models;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.MediaType;

@Entity
@Table(name = "t_message_medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageMedia {
    @Id
    @Column(name = "message_media_id", nullable = false)
    private String id;

    @Column(name = "path")
    private String path;

    @Column(name = "media_type")
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;
}
