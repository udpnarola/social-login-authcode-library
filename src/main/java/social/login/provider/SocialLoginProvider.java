package social.login.provider;

import lombok.Getter;
import lombok.Setter;
import social.login.dto.SocialLoginDetail;
import social.login.user.SocialUser;

@Getter
@Setter
public abstract class SocialLoginProvider {

    protected String clientId;
    protected String clientSecret;
    protected String redirectUri;

    public abstract SocialUser getUser(SocialLoginDetail socialLoginDetail);
}
