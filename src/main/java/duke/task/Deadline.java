package duke.task;

import duke.exception.InvalidTaskException;

public class Deadline extends Task {
    private String dueDate;

    public Deadline(String description, String dueDate) throws InvalidTaskException {
        super(description);
        this.dueDate = dueDate;
        validate();
    }

    public Deadline(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
        dueDate = input[3];
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
        if (!errorMessage.isBlank()) {
            throw new InvalidTaskException(errorMessage);
        }
    }

    // Getters/setters

    public String getDueDate() {
        return dueDate;
    }

    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + dueDate + ")";
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
