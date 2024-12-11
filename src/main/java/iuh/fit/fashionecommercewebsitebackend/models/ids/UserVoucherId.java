package iuh.fit.fashionecommercewebsitebackend.models.ids;

import iuh.fit.fashionecommercewebsitebackend.models.User;
import iuh.fit.fashionecommercewebsitebackend.models.Voucher;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserVoucherId implements Serializable {
    private User user;
    private Voucher voucher;
}
