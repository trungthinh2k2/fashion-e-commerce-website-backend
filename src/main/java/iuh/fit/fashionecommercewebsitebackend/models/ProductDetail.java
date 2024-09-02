package iuh.fit.fashionecommercewebsitebackend.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetail {

    @Id
    @Column(name = "product_detail_id", nullable = false)
    private String id;

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne()
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @ManyToOne()
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    @Column(name = "quantity")
    private Integer quantity;
}
