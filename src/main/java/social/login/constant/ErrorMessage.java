package social.login.constant;

public class ErrorMessage {

    public static final String ERR_EMPTY_OR_NULL_AUTH_CODE = "The auth code can not be empty or null";
    public static final String ERR_EMPTY_OR_NULL_OAUTH_TOKEN = "The oauth token can not be empty or null";
    public static final String ERR_EMPTY_OR_NULL_OAUTH_VERIFIER= "The oauth verifier can not be empty or null";
    public static final String ERR_GET_LINKEDIN_USER_DETAIL = "Unable to get Linkedin user details";
    public static final String ERR_GET_LINKEDIN_USER_EMAIL = "Unable to get Linkedin user email";
    public static final String ERR_GET_GOOGLE_USER_DETAIL = "Unable to get Google user details";
    public static final String ERR_GET_TWITTER_USER_DETAIL = "Unable to get Twitter user details";

    private ErrorMessage() {
    }
}
