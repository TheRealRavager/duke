package duke.task;

import duke.exception.InvalidTaskException;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String description, String dueDate) throws InvalidTaskException {
        super(description);
        this.dueDate = dueDate;
    }

    // Validations

    // Getters/setters

    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + dueDate + ")";
    }

    public String getDueDate() {
        return dueDate;
    }
    
    @Override
    public String toString() {
        return getInfo();
    }
}
