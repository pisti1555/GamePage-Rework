package projektek.GameSite.exceptions;

import java.util.Map;

public class JwtTokenException extends RuntimeException {
    private final Map<String, String> errors;

    public JwtTokenException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public JwtTokenException(Map<String, String> errors) {
        super("JWT token error");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
