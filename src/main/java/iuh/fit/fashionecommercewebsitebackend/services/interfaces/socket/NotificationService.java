package iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket;

import iuh.fit.fashionecommercewebsitebackend.models.Notification;

import java.util.List;

public interface NotificationService{
    List<Notification> getNotificationsByUserId(Long userId);
}
