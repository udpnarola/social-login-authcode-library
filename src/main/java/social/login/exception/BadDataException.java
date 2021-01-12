package social.login.exception;

public class BadDataException extends RuntimeException {

    public BadDataException(String errorMessage) {
        super(errorMessage);
    }
}
