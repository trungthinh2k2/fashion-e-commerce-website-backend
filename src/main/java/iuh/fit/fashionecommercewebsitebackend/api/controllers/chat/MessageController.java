package iuh.fit.fashionecommercewebsitebackend.api.controllers.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    @SendTo("/topic/messages")
    public Response sendMessage(@RequestBody MessageDto messageDto) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Message sent successfully",
                messageService.sendMessage(messageDto)
        );
    }

}
