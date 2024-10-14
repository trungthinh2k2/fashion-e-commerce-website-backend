package iuh.fit.fashionecommercewebsitebackend.models;

import iuh.fit.fashionecommercewebsitebackend.models.enums.MediaType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_comment_medias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentMedia {

    @Id
    @Column(name = "comment_media_id", nullable = false)
    private String id;

    @Column(name = "path")
    private String path;

    @Enumerated(EnumType.STRING)
    @Column(name = "media_type")
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

}
