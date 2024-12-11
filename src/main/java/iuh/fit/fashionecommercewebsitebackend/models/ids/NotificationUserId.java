package iuh.fit.fashionecommercewebsitebackend.models.ids;

import iuh.fit.fashionecommercewebsitebackend.models.Notification;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotificationUserId implements Serializable {

    private Notification notification;
    private User user;
}
