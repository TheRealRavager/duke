package duke.task;

import duke.exception.InvalidTaskException;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String description, String dueDate) throws InvalidTaskException {
        super(description);
        this.dueDate = dueDate;
        validate();
    }

    // Validations

    protected void validate() throws InvalidTaskException {
        String errorMessage = "";
        if (description.isBlank()) {
            errorMessage += "Description cannot be blank";
        }
        if (dueDate.isBlank()) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "Due date cannot be blank";
        }
        throw new InvalidTaskException(errorMessage);
    }

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
