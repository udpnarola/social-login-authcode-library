package social.login.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import social.login.dto.SocialLoginDetail;
import social.login.exception.BadDataException;
import social.login.exception.UserDetailException;
import social.login.user.SocialUser;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;

import static social.login.constant.ErrorMessage.*;

public class TwitterProvider extends SocialLoginProvider {

    public static final String OAUTH_TOKEN_KEY = "oauth_token";
    public static final String OAUTH_TOKEN_SECRET_KEY = "oauth_token_secret";
    public static final String OAUTH_VERIFIER_KEY = "oauth_verifier";
    public static final String ACCESS_TOKEN_URL = "https://api.twitter.com/oauth/access_token";

    public TwitterProvider(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public SocialUser getUser(SocialLoginDetail socialLoginDetail) {
        validateOauthTokenAndVerifier(socialLoginDetail);
        Map<String, String> accessTokenAndSecret = getAccessToken(socialLoginDetail);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb
                .setIncludeEmailEnabled(true)
                .setOAuthConsumerKey(clientId)
                .setOAuthConsumerSecret(clientSecret)
                .setOAuthAccessToken(accessTokenAndSecret.get(OAUTH_TOKEN_KEY))
                .setOAuthAccessTokenSecret(accessTokenAndSecret.get(OAUTH_TOKEN_SECRET_KEY));
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        try {
            User twitterUser = twitter.verifyCredentials();
            return prepareUser(twitterUser);
        } catch (TwitterException e) {
            throw new UserDetailException(ERR_GET_TWITTER_USER_DETAIL, e);
        }
    }

    private SocialUser prepareUser(User twitterUser) {
        SocialUser socialUser = new SocialUser();
        socialUser.setFirstName(twitterUser.getName());
        socialUser.setEmail(twitterUser.getEmail());
        socialUser.setImageUrl(twitterUser.getOriginalProfileImageURL());
        return socialUser;
    }

    private Map<String, String> getAccessToken(SocialLoginDetail socialLoginDetail) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add(OAUTH_TOKEN_KEY, socialLoginDetail.getOauthToken());
        requestParam.add(OAUTH_VERIFIER_KEY, socialLoginDetail.getOauthVerifier());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestParam, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(ACCESS_TOKEN_URL, request, String.class);

        String body = response.getBody();
        Map<String, String> accessTokenAndSecret = new HashMap<>();
        accessTokenAndSecret.put(OAUTH_TOKEN_KEY, StringUtils.substringBetween(body, OAUTH_TOKEN_KEY.concat("="), "&"));
        accessTokenAndSecret.put(OAUTH_TOKEN_SECRET_KEY, StringUtils.substringBetween(body, OAUTH_TOKEN_SECRET_KEY
                .concat("="), "&"));

        return accessTokenAndSecret;
    }

    private void validateOauthTokenAndVerifier(SocialLoginDetail socialLoginDetail) {
        if (StringUtils.isBlank(socialLoginDetail.getOauthToken()))
            throw new BadDataException(ERR_EMPTY_OR_NULL_OAUTH_TOKEN);
        if (StringUtils.isBlank(socialLoginDetail.getOauthVerifier()))
            throw new BadDataException(ERR_EMPTY_OR_NULL_OAUTH_VERIFIER);
    }

}
