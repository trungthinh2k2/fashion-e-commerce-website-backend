package iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket;

import iuh.fit.fashionecommercewebsitebackend.models.Comment;
import iuh.fit.fashionecommercewebsitebackend.models.CommentMedia;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Comment comment;
    private List<CommentMedia> commentMedia;
}
