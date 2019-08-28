package duke.exception;

/**
 * Raised when task attributes are invalid.
 */
public class InvalidTaskException extends Exception {
    public InvalidTaskException(String errorMessage) {
        super(errorMessage);
    }
}
