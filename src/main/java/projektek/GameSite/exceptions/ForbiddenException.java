package projektek.GameSite.exceptions;

import java.util.Map;

public class ForbiddenException extends RuntimeException {
    private Map<String, String> errors;

    public ForbiddenException() {
        super("Forbidden");
    }

    public ForbiddenException(Map<String, String> errors) {
        super("Forbidden");
        this.errors = errors;
    }

    public ForbiddenException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
