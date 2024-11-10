package iuh.fit.fashionecommercewebsitebackend.api.controllers.statisticals;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.Response;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.ResponseSuccess;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.statisticals.OrderStatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/statistical/orders")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class OrderStatisticalController {

    private final OrderStatisticalService orderStatisticalService;

    @GetMapping("/count/between")
    public Response getTotalOrdersBetweenDates(@RequestParam("startDate") String start,
                                           @RequestParam("endDate") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total orders between dates successfully",
                orderStatisticalService.countTotalOrdersBetweenDates(startDate, endDate)
        );
    }

    @GetMapping("/count/by-day")
    public Response getTotalOrdersByDay(@RequestParam("date") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total orders by day successfully",
                orderStatisticalService.countTotalOrdersByDay(dateTime)
        );
    }

    @GetMapping("/count/by-month")
    public Response getTotalOrdersByMonth(@RequestParam("year") int year,
                                          @RequestParam("month") int month) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total orders by month successfully",
                orderStatisticalService.countTotalOrdersByMonth(year, month)
        );
    }

    @GetMapping("/count/by-year")
    public Response getTotalOrdersByYear(@RequestParam("year") int year) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total orders by year successfully",
                orderStatisticalService.countTotalOrdersByYear(year)
        );
    }

    @GetMapping("/total-price/between")
    public Response getTotalPriceByOrderDateBetween(@RequestParam("startDate") String start,
                                                    @RequestParam("endDate") String end) {
        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total price by order date between successfully",
                orderStatisticalService.getTotalPriceByOrderDateBetween(startDate, endDate)
        );
    }

    @GetMapping("/total-price/by-day")
    public Response getTotalPriceByOrderDate(@RequestParam("date") String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total price by order date successfully",
                orderStatisticalService.getTotalPriceByOrderDate(dateTime)
        );
    }

    @GetMapping("/total-price/by-month")
    public Response getTotalPriceByOrderMonth(@RequestParam("year") int year,
                                              @RequestParam("month") int month) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total price by order month successfully",
                orderStatisticalService.getTotalPriceByOrderMonth(year, month)
        );
    }

    @GetMapping("/total-price/by-year")
    public Response getTotalPriceByOrderYear(@RequestParam("year") int year) {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get total price by order year successfully",
                orderStatisticalService.getTotalPriceByOrderYear(year)
        );
    }
}
