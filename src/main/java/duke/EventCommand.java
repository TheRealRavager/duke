package duke;

import java.time.LocalDateTime;

/**
 * Contains necessary information to build an event task.
 */
public class EventCommand extends Command {
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Creates a EventCommand with the input description, startDateTime and endDateTime.
     * @param description a short summary of the event.
     * @param startDateTime the start date and time of the event.
     * @param endDateTime the end date and time of the event.
     */
    public EventCommand(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(CommandType.EVENT);
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
}
