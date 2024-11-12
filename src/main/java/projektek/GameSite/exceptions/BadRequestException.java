package projektek.GameSite.exceptions;

import java.util.Map;

public class BadRequestException extends RuntimeException {
    private Map<String, String> errors;

    public BadRequestException() {
        super("Bad request");
    }

    public BadRequestException(Map<String, String> errors) {
        super("Bad request");
        this.errors = errors;
    }

    public BadRequestException(String message,  Map<String, String> errors) {
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
