package iuh.fit.fashionecommercewebsitebackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_product_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_price_id", nullable = false)
    private Integer id;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "discounted_price", columnDefinition = "DECIMAL(10,2)")
    private Double discountedPrice;


    @Column(name = "discounted_amount", columnDefinition = "decimal(10,2)")
    private Double discountedAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    private String note;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
