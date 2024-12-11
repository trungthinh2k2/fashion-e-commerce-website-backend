package iuh.fit.fashionecommercewebsitebackend.services.impl.orders;

import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import iuh.fit.fashionecommercewebsitebackend.repositories.UserVoucherRepository;
import iuh.fit.fashionecommercewebsitebackend.services.impl.BaseServiceImpl;
import iuh.fit.fashionecommercewebsitebackend.services.interfaces.orders.UserVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserVoucherServiceImpl extends BaseServiceImpl<UserVoucher, UserVoucherId> implements UserVoucherService {

    private UserVoucherRepository userVoucherRepository;

    public UserVoucherServiceImpl(JpaRepository<UserVoucher, UserVoucherId> repository) {
        super(repository, UserVoucher.class);
    }

    @Autowired
    public void setUserVoucherRepository(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
    }

    @Override
    public List<UserVoucher> findByUserId(Long userId) {
        return userVoucherRepository.findByUserId(userId);
    }
}
