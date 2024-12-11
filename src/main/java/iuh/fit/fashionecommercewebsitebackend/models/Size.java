package iuh.fit.fashionecommercewebsitebackend.models;


import iuh.fit.fashionecommercewebsitebackend.models.enums.SizeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_sizes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id", nullable = false)
    private Integer id;

    @Enumerated
    @Column(name = "size_type")
    private SizeType sizeType;

    @Column(name = "number_size")
    private Integer numberSize;

    @Column(name = "text_size", length = 50)
    private String textSize;

    public Size(int id) {
        this.id = id;
    }
}
