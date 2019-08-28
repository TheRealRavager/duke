package duke.exception;

// Exception that is thrown when input is incorrect (i.e. doesnt make sense)
public class InvalidTaskException extends Exception {
    public InvalidTaskException(String errorMessage) {
        super(errorMessage);
    }
}
