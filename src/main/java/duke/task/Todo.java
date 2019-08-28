package duke.task;

import duke.exception.InvalidTaskException;

public class Todo extends Task {
    public Todo(String description) throws InvalidTaskException {
        super(description);
        validate();
    }

    public Todo(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
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
