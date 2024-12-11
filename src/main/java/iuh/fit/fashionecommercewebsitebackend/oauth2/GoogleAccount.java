package iuh.fit.fashionecommercewebsitebackend.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GoogleAccount extends SocialAccount{

    private String picUrl;

    public GoogleAccount(String accountId, String email, String name, String picUrl) {
        super(accountId, email, name);
        this.picUrl = picUrl;
    }
}
