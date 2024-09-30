package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Order;
import iuh.fit.fashionecommercewebsitebackend.models.OrderDetail;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;
import java.util.List;

public interface OrderService extends BaseService<Order, String> {
    Order save(OrderDto orderDto) throws Exception;
    Order updateStatus(String id) throws DataNotFoundException;
    List<Order> getAllOrdersByEmail(String email);
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);
}
