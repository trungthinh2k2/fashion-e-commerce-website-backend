package iuh.fit.fashionecommercewebsitebackend.services.impl.chat;

import iuh.fit.fashionecommercewebsitebackend.models.Color;
import iuh.fit.fashionecommercewebsitebackend.models.MessageMedia;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.chat.MessageMediaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageMediaServiceImpl extends BaseServiceImpl<MessageMedia, String> implements MessageMediaService {

    public MessageMediaServiceImpl(JpaRepository<MessageMedia, String> repository) {
        super(repository, MessageMedia.class);
    }
}
