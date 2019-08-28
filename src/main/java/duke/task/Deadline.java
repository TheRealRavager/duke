package duke.task;

import duke.exception.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    public static DateTimeFormatter dueDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static DateTimeFormatter prettifiedDateTimeFormat = DateTimeFormatter.ofPattern("d MMM yy h:mma");
    private LocalDateTime dueDate;

    public Deadline(String description, LocalDateTime dueDate) throws InvalidTaskException {
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
        if (dueDate == null) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "Due date cannot be blank";
        }
        if (!errorMessage.isBlank()) {
            throw new InvalidTaskException(errorMessage);
        }
    }

    // Getters/setters

    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + getStringifiedDueDate() + ")";
    }

    public String getStringifiedDueDate() {
        return dueDate.format(prettifiedDateTimeFormat);
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
