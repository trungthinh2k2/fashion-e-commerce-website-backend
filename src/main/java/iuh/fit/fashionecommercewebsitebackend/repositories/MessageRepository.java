package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findByRoomChat_Id(Long roomChatId);

    @Query("SELECT m FROM Message m WHERE m.roomChat.roomId = :roomId ORDER BY m.messageTime ASC")
    List<Message> findByRoomId(@Param("roomId") String roomId);
}