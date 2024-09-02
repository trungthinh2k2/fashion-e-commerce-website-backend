package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.MessageMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageMediaRepository extends JpaRepository<MessageMedia, String> {
}