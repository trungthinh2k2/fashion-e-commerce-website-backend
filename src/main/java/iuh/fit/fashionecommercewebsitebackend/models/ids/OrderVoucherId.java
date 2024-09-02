package iuh.fit.fashionecommercewebsitebackend.models.ids;

import iuh.fit.fashionecommercewebsitebackend.models.Order;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderVoucherId implements Serializable {
    private Voucher voucher;
    private Order order;
}
