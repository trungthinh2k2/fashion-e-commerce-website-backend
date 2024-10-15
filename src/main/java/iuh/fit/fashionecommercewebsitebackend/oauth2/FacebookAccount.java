package iuh.fit.fashionecommercewebsitebackend.oauth2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class FacebookAccount extends SocialAccount {

    public FacebookAccount(String accountId, String email, String name) {
        super(accountId, email, name);
    }
}
