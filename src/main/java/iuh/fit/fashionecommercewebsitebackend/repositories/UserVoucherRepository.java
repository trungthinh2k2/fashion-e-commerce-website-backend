package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.UserVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserVoucherRepository extends JpaRepository<UserVoucher, UserVoucherId> {
    List<UserVoucher> findByUserId(Long userId);
}