package iuh.fit.fashionecommercewebsitebackend.models;


import iuh.fit.fashionecommercewebsitebackend.models.ids.OrderVoucherId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_order_vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(OrderVoucherId.class)
public class OrderVoucher {

    @Id
    @ManyToOne
    @JoinColumn(name = "voucher_id", nullable = false)
    private Voucher voucher;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
