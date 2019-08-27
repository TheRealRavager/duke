package duke.task;

import duke.exception.InvalidTaskException;

public class Event extends Task {
    // TODO: change to dateTime
    private String startDateTime;
    private String endDateTime;

    public Event(String description, String startDateTime, String endDateTime) throws InvalidTaskException {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        validate();
    }

    protected void validate() throws InvalidTaskException {
        String errorMessage = "";
        if (description.isBlank()) {
            errorMessage += "Description cannot be blank";
        }
        if (startDateTime.isBlank()) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "Start time cannot be blank";
        }
        if (endDateTime.isBlank()) {
            errorMessage += errorMessage.isBlank() ? "" : "\n";
            errorMessage += "End time cannot be blank";
        }
        if (!errorMessage.isBlank()) {
            throw new InvalidTaskException(errorMessage);
        }
    }

    // Getters/setters

    // TODO: display "date startTime-endTime" if same day, else "startDateTime - endDateTime"
    public String getInfo() {
        return "[E]" + super.getInfo() + "(at: " + startDateTime + "-" + endDateTime + ")";
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
