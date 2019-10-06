package duke;

import java.time.LocalDateTime;

/**
 * Contains necessary information to build a deadline task.
 */
public class DeadlineCommand extends Command {
    private String description;
    private LocalDateTime dueDate;


    /**
     * Creates a DeadlineCommand with the input description and dueDate.
     * @param description a short summary of what is due.
     * @param dueDate the date the task is due.
     */
    public DeadlineCommand(String description, LocalDateTime dueDate) {
        super(CommandType.DEADLINE);
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }
}
