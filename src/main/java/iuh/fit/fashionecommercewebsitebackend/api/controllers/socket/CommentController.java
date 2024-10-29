package iuh.fit.fashionecommercewebsitebackend.api.controllers.socket;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.socket.CommentDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket.CommentResponse;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CommentController {
    private final SimpMessagingTemplate messagingTemplate;
    private final CommentService commentService;

    @PostMapping("/send")
    public Response send(@ModelAttribute CommentDto commentDto)
            throws Exception{
        CommentResponse comment = commentService.createComment(commentDto);
        messagingTemplate.convertAndSend("/topic/product/" + commentDto.getProductId(),
                comment);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Comment has been sent successfully",
                comment
        );
    }


    @GetMapping("/page-comment")
    public Response pageComments(@RequestParam(defaultValue = "1") int pageNo,
                                @RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String[] search,
                                @RequestParam(required = false) String[] sort) throws Exception{
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get comment successfully",
                commentService.getDataWithPage(pageNo, pageSize, search, sort)
        );
    }
}
