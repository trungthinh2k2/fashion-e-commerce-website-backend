package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.RoomChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomChatRepository extends JpaRepository<RoomChat, Long> {
    Optional<RoomChat> findBySenderAndReceiver(String sender, String receiver);
}