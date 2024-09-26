package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ProductsOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.models.*;
import iuh.fit.fashionecommercewebsitebackend.models.enums.DeliveryMethod;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import iuh.fit.fashionecommercewebsitebackend.repositories.*;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, String> implements OrderService {

    private UserRepository userRepository;
    private AddressMapper addressMapper;
    private ProductDetailRepository productDetailRepository;
    private ProductRepository productRepository;
    private ProductPriceRepository productPriceRepository;
    private OrderDetailRepository orderDetailRepository;


    public OrderServiceImpl(JpaRepository<Order, String> repository) {
        super(repository, Order.class);
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

    @Override
    @Transactional(rollbackFor = {DataNotFoundException.class})
    public Order save(OrderDto orderDto) {

        List<ProductsOrderDto> productsOrderDtos = orderDto.getProductsOrderDtos();

        User user = userRepository.findByEmail(orderDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        double originalAmount = 0;

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .orderDate(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .paymentMethod(orderDto.getPaymentMethod())
                .note(orderDto.getNote())
                .phoneNumber(orderDto.getPhoneNumber())
                .buyerName(orderDto.getBuyerName())
                .originalAmount(originalAmount)
                .deliveryMethod(orderDto.getDeliveryMethod())
                .deliveryFee((double) (orderDto.getDeliveryMethod().equals(DeliveryMethod.EXPRESS) ? 30000 : 15000))
                .user(user)
                .address(addressMapper.addressDtoToAddress(orderDto.getAddress()))
                .build();
        order = super.save(order);

        originalAmount = handleAmount(productsOrderDtos, order,originalAmount);  // tien goc hoa don
        order.setOriginalAmount(originalAmount);
        order.setDiscountPrice(
                (originalAmount + order.getDeliveryFee()) - (order.getDiscountAmount() == null ? 0 : order.getDiscountAmount())
        );

        return super.save(order);
    }

    private double handleAmount(List<ProductsOrderDto> productsOrderDtos, Order order, double originalAmount) {
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
                for (ProductPrice productPrice: productPrices ) {
                    if(productPrice.getExpiredDate().isAfter(LocalDateTime.now())) {
                        if(productPrice.getDiscountedPrice() > discountedPrice) {
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
            orderDetailRepository.save(orderDetail);
            originalAmount += orderDetail.getTotal_amount();
        }
        return originalAmount;
    }

}
