package social.login.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import social.login.dto.SocialLoginDetail;
import social.login.exception.BadDataException;

import static social.login.constant.ErrorMessage.ERR_EMPTY_OR_NULL_AUTH_CODE;

public class SocialProviderUtil {

    private SocialProviderUtil() {
    }

    public static String getAccessToken(SocialLoginDetail socialLoginDetail, OAuth2ConnectionFactory<?> connectionFactory, String redirectUri) {
        String authCode = socialLoginDetail.getAuthCode();
        validateAuthCode(authCode);
        AccessGrant accessGrant = connectionFactory.getOAuthOperations().exchangeForAccess(authCode, redirectUri, null);
        return accessGrant.getAccessToken();
    }

    public static void validateAuthCode(String authCode) {
        if (StringUtils.isBlank(authCode))
            throw new BadDataException(ERR_EMPTY_OR_NULL_AUTH_CODE);
    }
}
