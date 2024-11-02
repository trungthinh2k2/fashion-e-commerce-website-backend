package iuh.fit.fashionecommercewebsitebackend.services.impl.socket;

import iuh.fit.fashionecommercewebsitebackend.models.NotificationUser;
import iuh.fit.fashionecommercewebsitebackend.repositories.NotificationUserRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.socket.UserNotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserNotificationServiceImpl implements UserNotificationService {

    private final NotificationUserRepository notificationUserRepository;

    public UserNotificationServiceImpl(NotificationUserRepository notificationUserRepository) {
        this.notificationUserRepository = notificationUserRepository;
    }

    @Override
    public void markNotificationAsRead(Integer notificationId, Long userId) {
        NotificationUser notificationUser = notificationUserRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationUser != null) {
            notificationUser.setIsRead(true);
            notificationUserRepository.save(notificationUser);
        }
    }

    @Override
    public List<NotificationUser> getNotificationsByUserId(Long userId) {
        return notificationUserRepository.findAllByUserId(userId);
    }
}
