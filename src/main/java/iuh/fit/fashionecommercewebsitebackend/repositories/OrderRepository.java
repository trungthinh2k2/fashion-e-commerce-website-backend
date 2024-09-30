package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByUserEmail(String email);
}