package projektek.GameSite.exceptions;

import java.util.Map;

public class NotFoundException extends RuntimeException {
    private Map<String, String> errors;

    public NotFoundException() {
        super("Not found");
    }

    public NotFoundException(String message, Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public NotFoundException(Map<String, String> errors) {
        super("Not found");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
