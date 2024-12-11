package iuh.fit.fashionecommercewebsitebackend.models;


import iuh.fit.fashionecommercewebsitebackend.models.ids.UserVoucherId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_user_vouchers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserVoucherId.class)
public class UserVoucher {

        @Id
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Id
        @ManyToOne
        @JoinColumn(name = "voucher_id", nullable = false)
        private Voucher voucher;

        @Column(name = "is_used")
        private Boolean isUsed;
}
