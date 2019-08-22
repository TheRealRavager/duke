package duke.task;

import duke.exception.InvalidTaskException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws InvalidTaskException {
        this.description = description;
        this.isDone = false;
        validate();
    }

    // Validations

    protected void validate() throws InvalidTaskException {
        if (description.isBlank()) {
            throw new InvalidTaskException("Description cannot be blank.");
        }
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
