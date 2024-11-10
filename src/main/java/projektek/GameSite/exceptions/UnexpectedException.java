package projektek.GameSite.exceptions;

public class UnexpectedException extends RuntimeException {
    public UnexpectedException() {
        super("Something went wrong");
    }

    public UnexpectedException(String message) {
        super(message);
    }
}
