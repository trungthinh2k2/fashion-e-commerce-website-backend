package iuh.fit.fashionecommercewebsitebackend.models.ids;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatbotUserId implements Serializable {

    private Long userId;
    private String chatbotId;
}
