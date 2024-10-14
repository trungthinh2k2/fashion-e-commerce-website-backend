package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByRoomChat_Id(Long roomChatId);
}