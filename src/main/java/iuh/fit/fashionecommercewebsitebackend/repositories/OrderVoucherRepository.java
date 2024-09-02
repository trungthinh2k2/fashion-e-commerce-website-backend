package iuh.fit.fashionecommercewebsitebackend.repositories;

import iuh.fit.fashionecommercewebsitebackend.models.OrderVoucher;
import iuh.fit.fashionecommercewebsitebackend.models.ids.OrderVoucherId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderVoucherRepository extends JpaRepository<OrderVoucher, OrderVoucherId> {
}