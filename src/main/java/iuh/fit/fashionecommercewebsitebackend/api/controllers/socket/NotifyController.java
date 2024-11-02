package iuh.fit.fashionecommercewebsitebackend.api.controllers.socket;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.NotificationService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.UserNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class NotifyController {

    private final NotificationService notificationService;
    private final UserNotificationService userNotificationService;

    @GetMapping("/user/{userId}")
    public Response getNotifications(@PathVariable Long userId ) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get notifications successfully",
                notificationService.getNotificationsByUserId(userId)
        );
    }

    @PutMapping("/mark-as-read/{notificationId}/user/{userId}")
    public Response markNotificationAsRead(@PathVariable Integer notificationId, @PathVariable Long userId) {
        userNotificationService.markNotificationAsRead(notificationId, userId);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "mark notification as read successfully",
                null
        );
    }

    @GetMapping("/notifications/user/{userId}")
    public Response getNotificationsByUser(@PathVariable Long userId ) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "get notifications successfully",
                notificationService.getNotificationsByUserId(userId)
        );
    }
}