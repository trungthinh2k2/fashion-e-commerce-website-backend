package iuh.fit.fashionecommercewebsitebackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "product-entity-graph",
                includeAllAttributes = true
        )
)
public class Product extends BaseModel implements Serializable {

    @Id
    @Column(name = "product_id", nullable = false)
    private String id;

    @Column(name = "product_name", length = 500)
    private String productName;

    @Column( name = "price", columnDefinition = "DECIMAL(10,2)")
    private Double price;

    @Column(name = "input_price", columnDefinition = "DECIMAL(10,2)")
    private Double inputPrice;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    @Column(name = "import_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime importDate;

    @Column(name = "product_name_convert", length = 500)
    private String productNameConvert;
}
