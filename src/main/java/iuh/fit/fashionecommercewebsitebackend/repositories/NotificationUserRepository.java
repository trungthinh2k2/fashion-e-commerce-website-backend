package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.NotificationUser;
import iuh.fit.fashionecommercewebsitebackend.models.ids.NotificationUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationUserRepository extends JpaRepository<NotificationUser, NotificationUserId> {
}