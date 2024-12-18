package iuh.fit.fashionecommercewebsitebackend.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.models.enums.DeliveryMethod;
import iuh.fit.fashionecommercewebsitebackend.models.enums.OrderStatus;
import iuh.fit.fashionecommercewebsitebackend.models.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @Column(name = "invoice_id", nullable = false)
    private String id;

    @Column(name = "invoice_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime invoiceDate;

    @Enumerated
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "buyer_name", length = 100)
    private  String buyerName;

    @Column(name = "address_detail", length = 500)
    private String addressDetail;

    @Column(name = "original_amount", columnDefinition = "decimal(10,2)")
    private Double originalAmount;

    @Column(name = "delivery_fee", columnDefinition = "decimal(10,2)")
    private Double deliveryFee;

    @Column(name = "discount_amount", columnDefinition = "decimal(10,2)")
    private Double discountAmount;

    @Column(name = "discount_price", columnDefinition = "decimal(10,2)")
    private Double discountPrice;

    @Column(name = "delivery_method")
    @Enumerated
    private DeliveryMethod deliveryMethod;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;
}
