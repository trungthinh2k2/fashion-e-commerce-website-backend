package iuh.fit.fashionecommercewebsitebackend.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import iuh.fit.fashionecommercewebsitebackend.models.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseModel{

    @Id
    @Column(name = "order_id", nullable = false)
    private String id;

    @Column(name = "order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;

    @Enumerated
    @Column(name = "order_status")
    private OrderStatus status;

    @Enumerated
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "note", length = 5000)
    private String note;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "buyer_name", length = 100)
    private  String buyerName;

    @Column(name = "original_amount", columnDefinition = "decimal(10,2)")
    private Double originalAmount;

    @Column(name = "delivery_fee", columnDefinition = "decimal(10,2)")
    private Double deliveryFee;

    @Column(name = "discount_amount", columnDefinition = "decimal(10,2)")
    private Double discountAmount;

    @Column(name = "discount_price", columnDefinition = "decimal(10,2)")
    private Double discountPrice;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
