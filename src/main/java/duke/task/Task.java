package duke.task;

import duke.exception.InvalidTaskException;

import java.util.LinkedList;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws InvalidTaskException {
        this.description = description;
        this.isDone = false;
        validate();
    }

    // Validations

    public void validate() throws InvalidTaskException {
        LinkedList<String> validityErrors = getValidityErrors();
        if (!isValid(validityErrors)) {
            throw new InvalidTaskException(prettifyValidityErrors(validityErrors));
        }
    }

    public LinkedList<String> getValidityErrors() {
        LinkedList<String> validityErrors = new LinkedList<>();
        if (description.isBlank()) {
            validityErrors.add("Description cannot be blank.");
        }
        return validityErrors;
    }

    protected String prettifyValidityErrors(LinkedList<String> validityErrors) {
        String prettyErrors = validityErrors.pop();
        for (String error : validityErrors) {
            prettyErrors += "\n" + error;
        }
        return prettyErrors;
    }

    public boolean isValid() {
        return getValidityErrors().isEmpty();
    }

    protected boolean isValid(LinkedList<String> validityErrors) {
        return validityErrors.isEmpty();
    }

    // Getter/setters

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String getInfo() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
