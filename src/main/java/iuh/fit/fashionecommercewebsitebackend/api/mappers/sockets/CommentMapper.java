package iuh.fit.fashionecommercewebsitebackend.api.mappers.sockets;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.socket.CommentDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Comment;
import iuh.fit.fashionecommercewebsitebackend.models.Product;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.repositories.ProductRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Comment commentDtoToComment(CommentDto commentDto) throws DataNotFoundException {
        User user = userRepository.findByEmail(commentDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Product product = productRepository.findById(commentDto.getProductId())
                .orElseThrow(() -> new DataNotFoundException("Product not found"));

        String id = "Cmt" + UUID.randomUUID().toString().substring(10);

        return Comment.builder()
                .id(id)
                .commentDate(LocalDateTime.now())
                .user(user)
                .product(product)
                .rating(commentDto.getRating())
                .textContent(commentDto.getContent())
                .build();
    }
}

