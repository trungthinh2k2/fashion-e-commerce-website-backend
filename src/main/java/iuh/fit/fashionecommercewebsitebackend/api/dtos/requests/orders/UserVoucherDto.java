package iuh.fit.fashionecommercewebsitebackend.api.dtos.requests.orders;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVoucherDto {
    private Long userId;
    private Long voucherId;
}
