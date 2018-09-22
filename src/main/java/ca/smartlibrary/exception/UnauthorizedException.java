package ca.smartlibrary.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String url) {
        super("Unauthorized for request [" + url + "]");
    }
}
