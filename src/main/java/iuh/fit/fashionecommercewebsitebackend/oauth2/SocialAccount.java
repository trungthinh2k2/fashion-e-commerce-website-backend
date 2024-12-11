package iuh.fit.fashionecommercewebsitebackend.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class SocialAccount {

    protected String accountId;
    protected String email;
    protected String name;

    public SocialAccount(String accountId, String email, String name) {
        this.accountId = accountId;
        this.email = email;
        this.name = name;
    }

}
