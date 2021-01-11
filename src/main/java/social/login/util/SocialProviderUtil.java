package social.login.util;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import social.login.dto.SocialLoginDetail;

public class SocialProviderUtil {

    private SocialProviderUtil() {
    }

    public static String getAccessToken(SocialLoginDetail socialLoginDetail, OAuth2ConnectionFactory<?> connectionFactory, String redirectUri) {
        String authCode = socialLoginDetail.getAuthCode();
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(authCode, redirectUri, null);
        return accessGrant.getAccessToken();
    }
}
