package duke.task;

import duke.exception.InvalidTaskException;

public class Todo extends Task {
    public Todo(String description) throws InvalidTaskException {
        super(description);
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
