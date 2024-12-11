package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountShipDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.VoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.response.socket.MessageResponse;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.api.mappers.orders.VoucherMapper;
import iuh.fit.fashionecommercewebsitebackend.models.Notification;
import iuh.fit.fashionecommercewebsitebackend.models.NotificationUser;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Scope;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import iuh.fit.fashionecommercewebsitebackend.repositories.NotificationRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.NotificationUserRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserRepository;
import iuh.fit.fashionecommercewebsitebackend.repositories.VoucherRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.VoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher, Long> implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationUserRepository notificationUserRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public VoucherServiceImpl(JpaRepository<Voucher, Long> repository, VoucherRepository voucherRepository,
                              VoucherMapper voucherMapper, NotificationRepository notificationRepository,
                              NotificationUserRepository notificationUserRepository,
                              SimpMessagingTemplate messagingTemplate, UserRepository userRepository) {
        super(repository, Voucher.class);
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.notificationRepository = notificationRepository;
        this.notificationUserRepository = notificationUserRepository;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public double applyDiscountOrder(ApplyDiscountOrderDto applyDiscountOrderDto) throws Exception {
        Voucher voucher = voucherRepository.findById(applyDiscountOrderDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
        if (voucher.getVoucherType() == VoucherType.FOR_PRODUCT) {
            if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) &&
                    applyDiscountOrderDto.getOriginalAmount() >= voucher.getMinOrderAmount()) {
                double discount = applyDiscountOrderDto.getOriginalAmount() * voucher.getDiscount() / 100;
                return Math.min(discount, voucher.getMaxDiscountAmount());
            }
        }
        return 0;
    }

    @Override
    public double applyDiscountShip(ApplyDiscountShipDto applyDiscountShipDto) throws Exception {
        Voucher voucher = voucherRepository.findById(applyDiscountShipDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
        if (voucher.getVoucherType() == VoucherType.FOR_DELIVERY) {
            if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) &&
                    applyDiscountShipDto.getOriginalAmount() >= voucher.getMinOrderAmount()) {
                double discount = applyDiscountShipDto.getDeliveryFee() * voucher.getDiscount() / 100;
                return Math.min(discount, voucher.getMaxDiscountAmount());
            }
        }
        return 0;
    }

    @Override
    public Voucher save(VoucherDto voucherDto) {
        Voucher voucher = voucherMapper.voucherDtoToVoucher(voucherDto);

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher data is invalid.");
        }

        voucherRepository.save(voucher);

        Notification notification = new Notification();
        notification.setNotificationTime(LocalDateTime.now());
        notification.setScope(Scope.ALL);

        String discountContent = "Bạn đang có mã giảm giá " + voucher.getDiscount() + "% dành cho ";
        if (voucher.getVoucherType() == VoucherType.FOR_PRODUCT) {
            discountContent += "đơn hàng";
        } else {
            discountContent += "phí vận chuyển";
        }
        notification.setContent(discountContent);

        notificationRepository.save(notification);

        List<User> allUsers = userRepository.findAll();

        for (User user : allUsers) {
            NotificationUser notificationUser = new NotificationUser();
            notificationUser.setNotification(notification);
            notificationUser.setIsRead(false);
            notificationUser.setUser(user);
            notificationUserRepository.save(notificationUser);
        }

        MessageResponse<Notification> messageResponse = new MessageResponse<>();
        messageResponse.setData(notification);
        messageResponse.setType("notification");

        messagingTemplate.convertAndSend("/topic/notifications", messageResponse);

        return voucher;
    }
}
