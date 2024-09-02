package iuh.fit.fashionecommercewebsitebackend.models;

import iuh.fit.fashionecommercewebsitebackend.models.ids.OrderDetailId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(OrderDetailId.class)
public class OrderDetail {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_detail_id", nullable = false)
    private ProductDetail productDetail;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_amount", columnDefinition = "DECIMAL(10,2)")
    private Double total_amount;
}
