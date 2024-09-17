package iuh.fit.fashionecommercewebsitebackend.models;

import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseModel{

    @Id
    @Column(name = "product_id", nullable = false)
    private String id;

    @Column(name = "product_name", length = 500)
    private String productName;

    @Column( name = "price", columnDefinition = "DECIMAL(10,2)")
    private Double price;

    @Column(name = "avg_rating", columnDefinition = "DECIMAL(10,1)")
    private Float avgRating;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "thumbnail", length = 500)
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    private Status productStatus;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "number_of_rating")
    private Integer numberOfRating;

    @Column(name = "buy_quantity")
    private Integer buyQuantity;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;
}
