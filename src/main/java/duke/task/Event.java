package duke.task;

import duke.exception.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    public static DateTimeFormatter eventDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    private static DateTimeFormatter prettifiedDateTimeFormat = DateTimeFormatter.ofPattern("d MMM yy h:mma");
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) throws InvalidTaskException {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        validate();
    }

    public Event(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
        startDateTime = LocalDateTime.parse(input[3], eventDateTimeFormat);
        endDateTime = LocalDateTime.parse(input[4], eventDateTimeFormat);
        validate();
    }
 
    protected void validate() throws InvalidTaskException {
        String errorMessage = "";
        if (description.isBlank()) {
            errorMessage += "Description cannot be blank";
        }
        if (startDateTime == null) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "Start time cannot be blank";
        }
        if (endDateTime == null) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "End time cannot be blank";
        }
        if (!errorMessage.isBlank()) {
            throw new InvalidTaskException(errorMessage);
        }
    }

    // Getters/setters

    public String getStringifiedStartDateTime() {
        return startDateTime.format(eventDateTimeFormat);
    }

    public String getStringifiedEndDateTime() {
        return endDateTime.format(eventDateTimeFormat);
    }

    // TODO: display "date startTime-endTime" if same day, else "startDateTime - endDateTime"
    public String getInfo() {
        return "[E]" + super.getInfo() + "(at: " + getPrettyStartDateTime() + "-" + getPrettyEndDateTime() + ")";
    }

    public String getPrettyStartDateTime() {
        return startDateTime.format(prettifiedDateTimeFormat);
    }

    public String getPrettyEndDateTime() {
        return endDateTime.format(prettifiedDateTimeFormat);
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
