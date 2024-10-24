package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountOrderDto;
import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.ApplyDiscountShipDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import iuh.fit.fashionecommercewebsitebackend.repositories.VoucherRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.VoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VoucherServiceImpl extends BaseServiceImpl<Voucher, Long> implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(JpaRepository<Voucher, Long> repository, VoucherRepository voucherRepository) {
        super(repository, Voucher.class);
        this.voucherRepository = voucherRepository;
    }

    @Override
    public double applyDiscountOrder(ApplyDiscountOrderDto applyDiscountOrderDto) throws Exception {
        Voucher voucher = voucherRepository.findById(applyDiscountOrderDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("Voucher not found"));
        if (voucher.getVoucherType() == VoucherType.FOR_PRODUCT) {
            if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) && applyDiscountOrderDto.getOriginalAmount() >= voucher.getMinOrderAmount()) {
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
            if (voucher.getExpiredDate().isAfter(LocalDateTime.now()) && applyDiscountShipDto.getOriginalAmount() >= voucher.getMinOrderAmount()) {
                double discount = applyDiscountShipDto.getDeliveryFee() * voucher.getDiscount() / 100;
                return Math.min(discount, voucher.getMaxDiscountAmount());
            }
        }
        return 0;
    }

}
