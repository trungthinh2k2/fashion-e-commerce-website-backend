package iuh.fit.fashionecommercewebsitebackend.api.controllers.chat;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.RoomChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room-chats")
@RequiredArgsConstructor
@SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
public class RoomChatController {

        private final RoomChatService roomChatService;

        @GetMapping
        public Response getAllRoomChat() {
            return new ResponseSuccess<>(
                    HttpStatus.OK.value(),
                    "success",
                    roomChatService.getAllRoomChat()
            );
        }
}
