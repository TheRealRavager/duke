package duke.task;

import duke.exception.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A deadline emulates a task that has to be completed by a specific due date.
 */
public class Deadline extends Task {
    /** Formatter used to parse input due dates. */
    public static DateTimeFormatter dueDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    /** Formatter used to prettify due dates. */
    private static DateTimeFormatter prettifiedDateTimeFormat = DateTimeFormatter.ofPattern("d MMM yy h:mma");
    /** Date time that a deadline has to be completed by. */
    private LocalDateTime dueDate;

    /**
     * Creates a deadline that is not yet completed and has the input description and duedate.
     * 
     * @param description a short summary of what is due.
     * @param dueDate the date the task is due.
     * @throws InvalidTaskException when description or due date is blank.
     */
    public Deadline(String description, LocalDateTime dueDate) throws InvalidTaskException {
        super(description);
        this.dueDate = dueDate;
        validate();
    }

    /**
     * Creates a deadline from the input string array.
     * 
     * @param input input string array containing description, isDone and due date.
     * @throws InvalidTaskException when description or due date is blank.
     */
    public Deadline(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
        dueDate = LocalDateTime.parse(input[3], dueDateFormat);
        validate();
    }

    // Validations

    /**
     * Validates that the created deadline object has acceptable parameters.
     * 
     * @throws InvalidTaskException when the deadline has unacceptable parameters.
     */
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

    /**
     * Returns the due date in string format.
     * 
     * @return the due date in string format.
     */
    public String getStringifiedDueDate() {
        return dueDate.format(dueDateFormat);
    }

    /**
     * Returns a nicely formatted string that displays the status and details of a deadline.
     * 
     * @return a nicely formatted string that displays the status and details of a deadline.
     */
    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + getPrettyDueDate() + ")";
    }

    /**
     * Returns the due date in a prettified string format.
     * 
     * @return the due date in a prettified string format.
     */
    public String getPrettyDueDate() {
        return dueDate.format(prettifiedDateTimeFormat);
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
