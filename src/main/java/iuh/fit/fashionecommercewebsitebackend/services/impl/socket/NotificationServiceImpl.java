package iuh.fit.fashionecommercewebsitebackend.services.impl.socket;

import iuh.fit.fashionecommercewebsitebackend.models.Notification;
import iuh.fit.fashionecommercewebsitebackend.repositories.NotificationRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.NotificationService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.UserNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;


    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

}
