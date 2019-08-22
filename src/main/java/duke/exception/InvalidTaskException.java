package duke.exception;

public class InvalidTaskException extends Exception {
    public InvalidTaskException(String errorMessage) {
        super(errorMessage);
    }
}
