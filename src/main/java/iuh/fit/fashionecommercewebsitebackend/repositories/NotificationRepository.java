package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "select n from Notification n " +
            "left join NotificationUser un " +
            "on n = un.notification " +
            "where un.user.id = :userId or n.scope = 'ALL'")
    List<Notification> findAllByUserId(Long userId);
}