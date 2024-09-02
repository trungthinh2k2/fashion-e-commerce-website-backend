package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.CommentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentMediaRepository extends JpaRepository<CommentMedia, String> {
}