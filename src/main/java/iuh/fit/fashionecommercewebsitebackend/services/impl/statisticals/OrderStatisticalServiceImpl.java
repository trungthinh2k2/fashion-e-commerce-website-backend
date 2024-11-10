package iuh.fit.fashionecommercewebsitebackend.services.impl.statisticals;

import iuh.fit.fashionecommercewebsitebackend.repositories.OrderRepository;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.statisticals.OrderStatisticalService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderStatisticalServiceImpl implements OrderStatisticalService {

    private final OrderRepository orderRepository;

    public OrderStatisticalServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public long countTotalOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.countOrdersBetweenDates(startDate, endDate);
    }

    @Override
    public long countTotalOrdersByDay(LocalDateTime date) {
        return orderRepository.countOrdersByDay(date);
    }

    @Override
    public long countTotalOrdersByMonth(int year, int month) {
        return orderRepository.countOrdersByMonth(year, month);
    }

    @Override
    public long countTotalOrdersByYear(int year) {
        return orderRepository.countOrdersByYear(year);
    }

    @Override
    public BigDecimal getTotalPriceByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getTotalPriceByOrderDateBetween(startDate, endDate);
    }

    @Override
    public BigDecimal getTotalPriceByOrderDate(LocalDateTime date) {
        return orderRepository.getTotalPriceByOrderDate(date);
    }

    @Override
    public BigDecimal getTotalPriceByOrderMonth(int year, int month) {
        return orderRepository.getTotalPriceByOrderMonth(year, month);
    }

    @Override
    public BigDecimal getTotalPriceByOrderYear(int year) {
        return orderRepository.getTotalPriceByOrderYear(year);
    }
}
