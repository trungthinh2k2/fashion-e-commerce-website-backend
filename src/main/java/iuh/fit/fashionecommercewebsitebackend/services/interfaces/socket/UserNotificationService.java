package iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket;

import iuh.fit.fashionecommercewebsitebackend.models.NotificationUser;

import java.util.List;

public interface UserNotificationService {
    void markNotificationAsRead(Integer notificationId, Long userId);
    List<NotificationUser> getNotificationsByUserId(Long userId);
    void deleteNotificationUser(Integer notificationId, Long userId);
    void deleteAllNotificationUser(Long userId);
}
