package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Order;
import iuh.fit.fashionecommercewebsitebackend.models.OrderDetail;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;
import java.util.List;

public interface OrderService extends BaseService<Order, String> {
    Order save(OrderDto orderDto) throws Exception;
    Order updateStatus(String id) throws DataNotFoundException;
    List<Order> getAllOrdersByEmailOrderByOrderDate(String email);
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);

    Order updateOrderStatus(String orderId, OrderUpdateDto status) throws DataNotFoundException;
    PageResponse<?> getOrdersForAdminRole(int pageNo, int pageSize, String[] search, String[] sort);
}
