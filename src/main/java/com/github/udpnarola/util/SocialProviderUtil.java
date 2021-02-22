package com.github.udpnarola.util;

import com.github.udpnarola.constant.ErrorMessage;
import com.github.udpnarola.exception.BadDataException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import com.github.udpnarola.dto.SocialLoginDetail;

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
            throw new BadDataException(ErrorMessage.ERR_EMPTY_OR_NULL_AUTH_CODE);
    }
}
