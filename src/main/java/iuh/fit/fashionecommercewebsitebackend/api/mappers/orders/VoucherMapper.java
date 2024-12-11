package iuh.fit.fashionecommercewebsitebackend.api.mappers.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.VoucherDto;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {
    public Voucher voucherDtoToVoucher(VoucherDto voucherDto) {
        return Voucher.builder()
                .name(voucherDto.getName())
                .note(voucherDto.getNote())
                .discount(voucherDto.getDiscount())
                .voucherType(voucherDto.getVoucherType())
                .scope(voucherDto.getScope())
                .startDate(voucherDto.getStartDate())
                .expiredDate(voucherDto.getExpiredDate())
                .maxDiscountAmount(voucherDto.getMaxDiscountAmount())
                .minOrderAmount(voucherDto.getMinOrderAmount())
                .quantity(voucherDto.getQuantity())
                .build();
    }
}
