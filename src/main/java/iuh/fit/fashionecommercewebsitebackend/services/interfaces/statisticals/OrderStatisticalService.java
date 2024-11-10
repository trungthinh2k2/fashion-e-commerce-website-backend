package iuh.fit.fashionecommercewebsitebackend.services.interfaces.statisticals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OrderStatisticalService {
    long countTotalOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    long countTotalOrdersByDay(LocalDateTime date);
    long countTotalOrdersByMonth(int year, int month);
    long countTotalOrdersByYear(int year);

    BigDecimal getTotalPriceByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    BigDecimal getTotalPriceByOrderDate(LocalDateTime date);
    BigDecimal getTotalPriceByOrderMonth(int year, int month);
    BigDecimal getTotalPriceByOrderYear(int year);
}
