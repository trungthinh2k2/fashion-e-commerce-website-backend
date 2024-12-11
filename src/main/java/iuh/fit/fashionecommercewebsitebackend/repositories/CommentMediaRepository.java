package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.CommentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentMediaRepository extends JpaRepository<CommentMedia, String> {
    List<CommentMedia> findAllByCommentId(String id);
}