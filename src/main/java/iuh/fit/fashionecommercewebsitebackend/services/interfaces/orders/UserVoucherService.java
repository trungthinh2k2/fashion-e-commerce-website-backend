package iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders;

import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.BaseService;

import java.util.List;

public interface UserVoucherService extends BaseService<UserVoucher, UserVoucherId> {
    List<UserVoucher> findByUserId(Long userId);
}
