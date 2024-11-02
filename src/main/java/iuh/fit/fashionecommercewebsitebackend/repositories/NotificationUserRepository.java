package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.NotificationUser;
import iuh.fit.fashionecommercewebsitebackend.models.ids.NotificationUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, NotificationUserId> {
    NotificationUser findByNotificationIdAndUserId(Integer notificationId, Long userId);
    List<NotificationUser> findAllByUserId(Long userId);
}