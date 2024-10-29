package iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.socket.CommentDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket.CommentResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Comment;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

import java.io.IOException;

public interface CommentService extends BaseService<Comment, String> {
    CommentResponse createComment(CommentDto commentDto) throws DataNotFoundException, IOException;
}
