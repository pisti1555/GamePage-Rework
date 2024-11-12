package projektek.GameSite.exceptions;

import java.util.Map;

public class UnauthorizedException extends RuntimeException {
    private Map<String, String> errors;

    public UnauthorizedException() {
        super("Unauthenticated");
    }

    public UnauthorizedException(Map<String, String> errors) {
        super("Unauthenticated");
        this.errors = errors;
    }

    public UnauthorizedException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
