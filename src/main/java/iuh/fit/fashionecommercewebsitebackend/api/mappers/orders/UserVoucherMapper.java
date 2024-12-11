package iuh.fit.fashionecommercewebsitebackend.api.mappers.orders;

import iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders.UserVoucherDto;
import iuh.fit.fashionecommercewebsitebackend.api.exceptions.DataNotFoundException;
import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.VoucherService;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserVoucherMapper {

    private final UserService userService;
    private final VoucherService voucherService;

    public UserVoucher userVoucherDtoToUserVoucher(UserVoucherDto userVoucherDto) throws DataNotFoundException {
        User user = userService.findById(userVoucherDto.getUserId())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        Voucher voucher = voucherService.findById(userVoucherDto.getVoucherId())
                .orElseThrow(() -> new DataNotFoundException("voucher not found"));
        UserVoucher userVoucher = new UserVoucher();
        userVoucher.setIsUsed(false);
        userVoucher.setVoucher(voucher);
        userVoucher.setUser(user);
        return userVoucher;
    }
}
