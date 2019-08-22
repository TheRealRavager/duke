package duke.task;

import duke.exception.InvalidTaskException;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String description, String dueDate) throws InvalidTaskException {
        super(description);
        this.dueDate = dueDate;
    }

    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + dueDate + ")";
    }

    public String getDueDate() {
        return dueDate;
    }
}
