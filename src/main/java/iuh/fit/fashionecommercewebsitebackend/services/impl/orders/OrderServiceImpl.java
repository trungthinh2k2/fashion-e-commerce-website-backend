package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.OrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ProductsOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.AddressMapper;
import iuh.fit.fashionecommercewebsitebackend.models.*;
import iuh.fit.fashionecommercewebsitebackend.models.enums.DeliveryMethod;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Scope;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import iuh.fit.fashionecommercewebsitebackend.repositories.*;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Order save(OrderDto orderDto) throws Exception {

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
                            double discountFee = addVoucherDelivery( order.getDeliveryFee(), voucher, originalAmount);
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
                } else  {
                    UserVoucherId userVoucherId1 = new UserVoucherId(user, voucher);
                    Optional<UserVoucher> userVoucher = userVoucherRepository.findById(userVoucherId1);
                    if (userVoucher.isEmpty()) {
                        if (voucher.getVoucherType().equals(VoucherType.FOR_DELIVERY)) {
                            double discountFee = addVoucherDelivery( order.getDeliveryFee(), voucher, originalAmount);
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

    private double handleAmount(List<ProductsOrderDto> productsOrderDtos, Order order, double originalAmount) throws DataNotFoundException {
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

    private double addVoucherDelivery(double discount, Voucher voucher, double originalAmount) {
        if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) && originalAmount >= voucher.getMinOrderAmount()) {
            double discountF = discount * voucher.getDiscount()/100;
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
            double discountedAmount = originalAmount * voucher.getDiscount()/100;
            if ((discountedAmount >= voucher.getMaxDiscountAmount())) {
                return voucher.getMaxDiscountAmount();
            } else {
                return discountedAmount;
            }
        }
        return 0;
    }
}
