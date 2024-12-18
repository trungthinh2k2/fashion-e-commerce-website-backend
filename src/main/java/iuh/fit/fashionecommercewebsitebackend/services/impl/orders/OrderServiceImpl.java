package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderUpdateDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ProductsOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.PageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket.MessageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.models.*;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import iuh.fit.fashionecommercewebsitebackend.models.enums.PaymentMethod;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Scope;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import iuh.fit.fashionecommercewebsitebackend.repositories.*;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.EmailService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.OrderService;
import iuh.fit.fashionecommercewebsitebackend.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    private UserRepository userRepository;
    private AddressMapper addressMapper;
    private VoucherRepository voucherRepository;
    private UserVoucherRepository userVoucherRepository;
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    private ProductPriceRepository productPriceRepository;
    private OrderDetailRepository orderDetailRepository;
    private OrderRepository orderRepository;
    private NotificationRepository notificationRepository;
    private SimpMessagingTemplate messagingTemplate;
    private NotificationUserRepository userNotificationRepository;
    private InvoiceRepository invoiceRepository;
    private final EmailService emailService;

    public OrderServiceImpl(JpaRepository<Order, String> repository, EmailService emailService) {
        super(repository, Order.class);
        this.emailService = emailService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAddressMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Autowired
    public void setVoucherRepository(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Autowired
    public void setUserVoucherRepository(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
    }

    @Autowired
    public void setProductDetailRepository(ProductDetailRepository productDetailRepository) {
        this.productDetailRepository = productDetailRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductPriceRepository(ProductPriceRepository productPriceRepository) {
        this.productPriceRepository = productPriceRepository;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Autowired
    public void setUserNotificationRepository(NotificationUserRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Autowired
    public void setInvoiceRepository(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Order save(OrderDto orderDto) throws Exception {

        List<ProductsOrderDto> productsOrderDtos = orderDto.getProductsOrderDtos();

        User user = userRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        double originalAmount = 0;
        String id = "ORD" + LocalDate.now().format(DateTimeFormatter.ofPattern("_ddMMyyyy_")) + UUID.randomUUID().toString().substring(0, 8);
        LocalDate estimatedDeliveryDate = calculateEstimatedDeliveryDate(orderDto);
        Order order = Order.builder()
                .id(id)
                .orderDate(LocalDateTime.now())
                .status(orderDto.getPaymentMethod() == PaymentMethod.CC ? OrderStatus.NOT_PROCESSED_YET : OrderStatus.PENDING)
                .paymentMethod(orderDto.getPaymentMethod())
                .note(orderDto.getNote())
                .phoneNumber(orderDto.getPhoneNumber())
                .buyerName(orderDto.getBuyerName())
                .originalAmount(originalAmount)
                .deliveryMethod(orderDto.getDeliveryMethod())
                .deliveryFee(orderDto.getDeliveryFee())
                .user(user)
                .address(addressMapper.addressDtoToAddress(orderDto.getAddress()))
                .addressDetail(orderDto.getAddressDetail())
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .build();


        order = super.save(order);
        originalAmount = handleAmount(productsOrderDtos, order, originalAmount);

        List<Long> vouchers = orderDto.getVouchers();
        if (vouchers != null) {
            if (vouchers.size() > 2) {
                throw new Exception("Maximum 2 vouchers");
            }
            boolean hasProductVoucher = false;
            boolean hasDeliveryVoucher = false;
            for (Long voucherId : vouchers) {
                Voucher voucher = voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
                UserVoucherId userVoucherId = new UserVoucherId(user, voucher);

                if (voucher.getVoucherType().equals(VoucherType.FOR_PRODUCT)) {
                    if (hasProductVoucher) {
                        throw new Exception("Only one FOR_PRODUCT voucher is allowed");
                    }
                    hasProductVoucher = true;
                } else if (voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                    if (hasDeliveryVoucher) {
                        throw new Exception("Only one FOR_DELIVERY voucher is allowed");
                    }
                    hasDeliveryVoucher = true;
                }

                if (voucher.getScope().equals(Scope.FOR_USER)) {
                    UserVoucher userVoucher = userVoucherRepository.findById(userVoucherId)
                            .orElseThrow(() -> new DataNotFoundException("UserVoucher not found"));
                    if (!userVoucher.getIsUsed()) {
                        if (voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discountFee = addVoucherDelivery(order.getDeliveryFee(), voucher, originalAmount);
                            order.setDeliveryFee(order.getDeliveryFee() - discountFee);
                            order.setDiscountAmount(order.getDiscountAmount() == null ? discountFee : order.getDiscountAmount() + discountFee);
                            if (discountFee > 0) {
                                userVoucher.setIsUsed(true);
                            }
                        } else {
                            double discountOrder = addVoucherOrder(originalAmount, voucher);
                            order.setDiscountAmount(
                                    (order.getDiscountAmount() == null ? 0 : order.getDiscountAmount()) + discountOrder
                            );
                            if (discountOrder > 0) {
                                userVoucher.setIsUsed(true);
                            }
                        }
                        userVoucherRepository.save(userVoucher);
                        int quantityStock = voucher.getQuantity() - 1;
                        voucher.setQuantity(quantityStock);
                        voucherRepository.save(voucher);
                    }
                } else {
                    UserVoucherId userVoucherId1 = new UserVoucherId(user, voucher);
                    Optional<UserVoucher> userVoucher = userVoucherRepository.findById(userVoucherId1);
                    if (userVoucher.isEmpty()) {
                        if (voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discountFee = addVoucherDelivery(order.getDeliveryFee(), voucher, originalAmount);
                            order.setDeliveryFee(order.getDeliveryFee() - discountFee);
                            order.setDiscountAmount(order.getDiscountAmount() == null ? discountFee : order.getDiscountAmount() + discountFee);
                        } else {
                            double discountOrder = addVoucherOrder(originalAmount, voucher);
                            order.setDiscountAmount(
                                    (order.getDiscountAmount() == null ? 0 : order.getDiscountAmount()) + discountOrder
                            );
                        }
                        UserVoucher voucherUsages = new UserVoucher();
                        voucherUsages.setVoucher(voucher);
                        voucherUsages.setUser(user);
                        voucherUsages.setIsUsed(true);
                        userVoucherRepository.save(voucherUsages);

                        int quantityStock = voucher.getQuantity() - 1;
                        voucher.setQuantity(quantityStock);
                        voucherRepository.save(voucher);
                    }
                }
            }
        }

        order.setOriginalAmount(originalAmount);
        order.setDiscountPrice(
                (originalAmount + order.getDeliveryFee()) - (order.getDiscountAmount() == null ? 0 : order.getDiscountAmount())
        );

        return super.save(order);
    }

    @Override
    public Order updateStatus(String id) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        LocalDateTime now = LocalDateTime.now();
        if (order.getStatus().equals(OrderStatus.CANCELLED)) {
            throw new DataNotFoundException("Order has been cancelled");
        }
        if (order.getStatus().equals(OrderStatus.PENDING) && order.getOrderDate().plusHours(2).isAfter(now)) {
            returnProductsToStockByOrderId(id);
            order.setStatus(OrderStatus.CANCELLED);
        } else
            throw new DataNotFoundException("Order can not be cancelled");
        return orderRepository.save(order);
    }

    @Override
    public Order updateReceivedStatus(String id) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (order.getStatus().equals(OrderStatus.DELIVERED)) {
            order.setStatus(OrderStatus.RECEIVED);
        } else
            throw new DataNotFoundException("Order can not be updated to received status");
        return orderRepository.save(order);
    }

    @Override
    public Order updateStatusPayment(String id) throws DataNotFoundException {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        if (order.getStatus().equals(OrderStatus.NOT_PROCESSED_YET)) {
            order.setStatus(OrderStatus.PENDING);
        }
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrdersByEmailOrderByOrderDate(String email) {
        return orderRepository.findAllByUserEmailOrderByOrderDateDesc(email);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Order updateOrderStatus(String id, OrderUpdateDto status) throws Exception {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));
        order.setStatus(status.getOrderStatus());
        orderRepository.save(order);

        if (status.getOrderStatus() == OrderStatus.PROCESSING) {
            Invoice invoice = createInvoiceForOrder(order);
            emailService.sendHtmlMailInvoice(invoice);
        }

        handleNotification(order, "Đơn hàng " + order.getId() + " của bạn " + OrderStatus.fromString(status.getOrderStatus().name()).getStatusInVietnamese().toLowerCase());
        return order;
    }

    private Invoice createInvoiceForOrder(Order order) {
        Invoice invoice = new Invoice();
        invoice.setId("INV_" + order.getId());
        invoice.setOrder(order);
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setPaymentMethod(order.getPaymentMethod());
        invoice.setBuyerName(order.getBuyerName());
        invoice.setPhoneNumber(order.getPhoneNumber());
        invoice.setAddressDetail(order.getAddressDetail());
        invoice.setOriginalAmount(order.getOriginalAmount());
        invoice.setDeliveryFee(order.getDeliveryFee());
        invoice.setDiscountAmount(order.getDiscountAmount());
        invoice.setDiscountPrice(order.getDiscountPrice());
        invoice.setDeliveryMethod(order.getDeliveryMethod());
        return invoiceRepository.save(invoice);
    }

    @Override
    public PageResponse<?> getOrdersForAdminRole(int pageNo, int pageSize, String[] search, String[] sort) {
        return null;
    }


    private double handleAmount(List<ProductsOrderDto> productsOrderDtos, Order order, double originalAmount)
            throws DataNotFoundException {
        for (ProductsOrderDto productsOrderDto : productsOrderDtos) {
            ProductDetail productDetail = productDetailRepository.findById(productsOrderDto.getProductDetailId())
                    .orElseThrow(() -> new DataNotFoundException("Product detail not found"));
            Product product = productDetail.getProduct();

            int quantity = productDetail.getQuantity() - productsOrderDto.getQuantity();
            if (quantity < 0) {
                throw new DataNotFoundException("Product out of stock");
            }
            productDetail.setQuantity(quantity);
            productDetailRepository.save(productDetail);

            product.setTotalQuantity(product.getTotalQuantity() - productsOrderDto.getQuantity());
            int buyQuantity = product.getBuyQuantity() == null ? 0 : product.getBuyQuantity();
            product.setBuyQuantity(buyQuantity + productsOrderDto.getQuantity());
            productRepository.save(product);

            List<ProductPrice> productPrices = productPriceRepository
                    .findAllByProductId(product.getId());
            double discountedPrice = 0;
            double price = product.getPrice();
            if (!productPrices.isEmpty()) {
                for (ProductPrice productPrice : productPrices) {
                    if (productPrice.getExpiredDate().isAfter(LocalDateTime.now())) {
                        if (productPrice.getDiscountedPrice() > discountedPrice) {
                            discountedPrice = productPrice.getDiscountedPrice();
                        }
                    }
                }
            }
            price = price - discountedPrice;
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(productsOrderDto.getQuantity());
            orderDetail.setTotal_amount(price * orderDetail.getQuantity());
            orderDetail.setOrder(order);
            orderDetail.setProductDetail(productDetail);
            orderDetail.setPriceAtCreateOrder(price);
            orderDetailRepository.save(orderDetail);
            originalAmount += orderDetail.getTotal_amount();
        }
        return originalAmount;
    }

    private double addVoucherDelivery(double discount, Voucher voucher, double originalAmount) {
        if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) && originalAmount >= voucher.getMinOrderAmount()) {
            double discountF = discount * voucher.getDiscount() / 100;
            if ((discountF >= voucher.getMaxDiscountAmount())) {
                return voucher.getMaxDiscountAmount();
            } else {
                return discountF;
            }
        }
        return 0;
    }

    private double addVoucherOrder(double originalAmount, Voucher voucher) {
        if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) && originalAmount >= voucher.getMinOrderAmount()) {
            double discountedAmount = originalAmount * voucher.getDiscount() / 100;
            if ((discountedAmount >= voucher.getMaxDiscountAmount())) {
                return voucher.getMaxDiscountAmount();
            } else {
                return discountedAmount;
            }
        }
        return 0;
    }

    @Override
    public void returnProductsToStockByOrderId(String orderId) throws DataNotFoundException {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        for (OrderDetail orderDetail : orderDetails) {
            ProductDetail productDetail = orderDetail.getProductDetail();

            int updatedQuantity = productDetail.getQuantity() + orderDetail.getQuantity();
            productDetail.setQuantity(updatedQuantity);
            productDetailRepository.save(productDetail);

            Product product = productDetail.getProduct();
            product.setTotalQuantity(product.getTotalQuantity() + orderDetail.getQuantity());
            product.setBuyQuantity(product.getBuyQuantity() - orderDetail.getQuantity());
            productRepository.save(product);
        }
    }

    private LocalDate calculateEstimatedDeliveryDate(OrderDto orderDto) {
        int processingDays = 1; // Số ngày xử lý đơn hàng
        int deliveryDays;

        switch (orderDto.getDeliveryMethod()) {
            case EXPRESS: // Giao nhanh
                deliveryDays = 3;
                break;
            case ECONOMY: // Giao thường
                deliveryDays = 7;
                break;
            default:
                throw new IllegalArgumentException("Unknown delivery method: " + orderDto.getDeliveryMethod());
        }

        // Tính ngày giao hàng
        LocalDate orderDate = LocalDate.now().plusDays(processingDays);
        LocalDate estimatedDate = orderDate.plusDays(deliveryDays);

        // Loại bỏ ngày cuối tuần (nếu không giao vào cuối tuần)
        while (estimatedDate.getDayOfWeek() == DayOfWeek.SATURDAY || estimatedDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
            estimatedDate = estimatedDate.plusDays(1);
        }
        return estimatedDate;
    }

    private void handleNotification(Order order, String text) {
        Notification notification = new Notification();
        notification.setContent(text);
        notification.setScope(Scope.FOR_USER);
        notification.setNotificationTime(LocalDateTime.now());
        notificationRepository.save(notification);
        NotificationUser notificationUser = new NotificationUser();
        notificationUser.setNotification(notification);
        notificationUser.setUser(order.getUser());
        notificationUser.setIsRead(false);
        userNotificationRepository.save(notificationUser);
        MessageResponse<Notification> messageResponse = new MessageResponse<>();
        messageResponse.setData(notification);
        messageResponse.setType("notification");
        messagingTemplate.convertAndSendToUser(order.getUser().getEmail(),
                "/queue/notifications", messageResponse);
    }
}
