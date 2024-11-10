package projektek.GameSite.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthenticated");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
