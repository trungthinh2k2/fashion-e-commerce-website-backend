package iuh.fit.fashionecommercewebsitebackend.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Gender;
import iuh.fit.fashionecommercewebsitebackend.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", unique = true, length = 50)
    private String email;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "username", length = 50)
    private String username;

    @JsonIgnore
    @Column(name = "password", length = 1000)
    private String password;

    @Column(name = "date_of_birth", length = 50)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "verify")
    private boolean verify;

    @JsonIgnore
    @Column(name = "otp", length = 10)
    private String otp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

}
