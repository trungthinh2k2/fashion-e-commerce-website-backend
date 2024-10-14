package iuh.fit.fashionecommercewebsitebackend.api.controllers.chat;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.chat.MessageDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageService;
import iuh.fit.fashionecommercewebsitebackend.utils.ValidToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@SecurityRequirements(@SecurityRequirement(name = "bearerAuth"))
public class MessageController {

    private final ValidToken validToken;
    private final MessageService messageService;

    @PostMapping("/send")
    public Response senMessage(@ModelAttribute @Valid MessageDto messageDto,
                               HttpServletRequest req)
            throws Exception {
        validToken.validToken(messageDto.getSender(), req);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "success",
                messageService.sendMessage(messageDto)
        );
    }
}
