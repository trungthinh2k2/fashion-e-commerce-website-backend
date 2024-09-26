package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.UserVoucherService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherServiceImpl extends BaseServiceImpl<UserVoucher, UserVoucherId> implements UserVoucherService {
    public UserVoucherServiceImpl(JpaRepository<UserVoucher, UserVoucherId> repository) {
        super(repository, UserVoucher.class);
    }
}
