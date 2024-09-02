package iuh.fit.fashionecommercewebsitebackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "district", length = 50)
    private String district;

    @Column(name = "city", length = 50)
    private String city;
}
