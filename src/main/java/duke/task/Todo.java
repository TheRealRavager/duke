package duke.task;

import duke.exception.InvalidTaskException;

public class Todo extends Task {
    public Todo(String description) throws InvalidTaskException {
        super(description);
    }

    public String getInfo() {
        return "[T]" + super.getInfo();
    }
}
