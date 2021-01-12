package social.login.provider;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import social.login.dto.SocialLoginDetail;
import social.login.exception.UserDetailException;
import social.login.user.SocialUser;
import social.login.util.SocialProviderUtil;

import java.io.IOException;

import static social.login.constant.ErrorMessage.ERR_GET_GOOGLE_USER_DETAIL;

public class GoogleProvider extends SocialLoginProvider {

    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    public GoogleProvider(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public SocialUser getUser(SocialLoginDetail socialLoginDetail) {
        String authCode = socialLoginDetail.getAuthCode();
        SocialProviderUtil.validateAuthCode(authCode);
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(HTTP_TRANSPORT,
                    JSON_FACTORY, TOKEN_URL, clientId, clientSecret, authCode, redirectUri)
                    .execute();

            GoogleIdToken.Payload payload = tokenResponse.parseIdToken().getPayload();
            return prepareUser(payload);
        } catch (IOException e) {
            throw new UserDetailException(ERR_GET_GOOGLE_USER_DETAIL, e);
        }
    }

    private SocialUser prepareUser(GoogleIdToken.Payload payload) {
        SocialUser socialUser = new SocialUser();
        socialUser.setFirstName((String) payload.get("given_name"));
        socialUser.setLastName((String) payload.get("family_name"));
        socialUser.setEmail((String) payload.get("email"));
        socialUser.setImageUrl(((String) payload.get("picture"))
                .replaceFirst("(=).*$", "=s400-c"));
        return socialUser;
    }
}
