package iuh.fit.fashionecommercewebsitebackend.models;


import iuh.fit.fashionecommercewebsitebackend.models.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

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
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private Message message;
}
