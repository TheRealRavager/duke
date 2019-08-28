package duke.task;

import duke.exception.InvalidTaskException;

public class Todo extends Task {
    public Todo(String description, Boolean isDone) throws InvalidTaskException {
        super(description, isDone);
        validate();
    }

    protected void validate() throws InvalidTaskException {
        if (description.isBlank()) {
            throw new InvalidTaskException("Description cannot be blank");
        }
    }

    // Getters/setters

    public String getInfo() {
        return "[T]" + super.getInfo();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
