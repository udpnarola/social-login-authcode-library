package social.login.provider;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import social.login.dto.SocialLoginDetail;
import social.login.user.SocialUser;
import social.login.util.SocialProviderUtil;

import java.util.Map;

public class FacebookProvider extends SocialLoginProvider {

    private FacebookConnectionFactory connectionFactory;

    public FacebookProvider(String clientId, String clientSecret, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.connectionFactory = new FacebookConnectionFactory(clientId, clientSecret);
    }

    public SocialUser getUser(SocialLoginDetail socialLoginDetail) {
        String accessToken = SocialProviderUtil.getAccessToken(socialLoginDetail, connectionFactory, redirectUri);
        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"email", "first_name", "last_name", "picture.width(400).height(400)"};
        User user = facebook.fetchObject("me", User.class, fields);
        return prepareSocialUser(user);
    }

    private SocialUser prepareSocialUser(User user) {
        SocialUser socialUser = new SocialUser();
        socialUser.setFirstName(user.getFirstName());
        socialUser.setLastName(user.getLastName());
        socialUser.setEmail(user.getEmail());

        Map<String, Map<String, String>> picture = (Map<String, Map<String, String>>) user.getExtraData().get("picture");
        Map<String, String> pictureDetails = picture.get("data");

        socialUser.setImageUrl(pictureDetails.get("url"));

        return socialUser;
    }
}
