package com.github.udpnarola.provider;

import com.github.udpnarola.dto.SocialLoginDetail;
import com.github.udpnarola.user.SocialUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SocialLoginProvider {

    protected String clientId;
    protected String clientSecret;
    protected String redirectUri;

    public abstract SocialUser getUser(SocialLoginDetail socialLoginDetail);
}
