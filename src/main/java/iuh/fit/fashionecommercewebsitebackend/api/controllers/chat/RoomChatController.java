package iuh.fit.fashionecommercewebsitebackend.api.controllers.chat;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.RoomChatDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.configs.docs.CreateResponse;
import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.RoomChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/room-chats")
@RequiredArgsConstructor
public class RoomChatController {

}
