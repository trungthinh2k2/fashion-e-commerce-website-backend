package iuh.fit.fashionecommercewebsitebackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Scope;
import iuh.fit.fashionecommercewebsitebackend.models.enums.VoucherType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Voucher extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "note", length = 1000)
    private String note;

    @Column(name = "discount")
    private Integer discount;

    @Enumerated
    @Column(name = "voucher_type")
    private VoucherType voucherType;

    @Column(name = "start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @Column(name = "expired_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredDate;

    @Column(name = "quantity")
    private Integer quantity;

    @Enumerated
    @Column(name = "scope")
    private Scope scope;

    @Column(name = "max_discount_amount", columnDefinition = "decimal(10,2)")
    private Double maxDiscountAmount;

    @Column(name = "min_order_amount", columnDefinition = "decimal(10,2)")
    private Double minOrderAmount;
}

