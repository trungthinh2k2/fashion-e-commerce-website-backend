package iuh.fit.fashionecommercewebsitebackend.models;


import iuh.fit.fashionecommercewebsitebackend.models.ids.NotificationUserId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_notification_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(NotificationUserId.class)
public class NotificationUser {

        @Id
        @ManyToOne
        @JoinColumn(name = "notification_id", nullable = false)
        private Notification notification;

        @Id
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        private Boolean isRead;
}
