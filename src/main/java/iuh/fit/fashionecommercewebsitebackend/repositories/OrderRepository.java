package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.Order;
import iuh.fit.fashionecommercewebsitebackend.repositories.customizations.BaseCustomizationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String>{
    List<Order> findAllByUserEmailOrderByOrderDateDesc(String email);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    long countOrdersBetweenDates(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(o) FROM Order o WHERE FUNCTION('DATE', o.orderDate) = FUNCTION('DATE', :date)")
    long countOrdersByDay(@Param("date") LocalDateTime date);

    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    long countOrdersByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT COUNT(o) FROM Order o WHERE YEAR(o.orderDate) = :year")
    long countOrdersByYear(@Param("year") int year);

    @Query("SELECT SUM(o.discountPrice) " +
            "FROM Order o WHERE o.status = 4 AND o.orderDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPriceByOrderDateBetween(@Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);

    @Query("SELECT SUM(o.discountPrice) " +
            "FROM Order o WHERE o.status = 4 AND FUNCTION('DATE', o.orderDate) = FUNCTION('DATE', :date)")
    BigDecimal getTotalPriceByOrderDate(@Param("date") LocalDateTime date);

    @Query("SELECT SUM(o.discountPrice) " +
            "FROM Order o WHERE o.status = 4 AND YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month")
    BigDecimal getTotalPriceByOrderMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT SUM(o.discountPrice) " +
            "FROM Order o WHERE o.status = 4 AND YEAR(o.orderDate) = :year")
    BigDecimal getTotalPriceByOrderYear(@Param("year") int year);
}