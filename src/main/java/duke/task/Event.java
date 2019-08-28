package duke.task;

import duke.exception.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Event emulates a task to be attended to that has a start and end date/time.
 */
public class Event extends Task {
    /** Formatter used to parse input event date/times. */
    public static DateTimeFormatter eventDateTimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
    /** Formatter used to prettify due dates. */
    private static DateTimeFormatter prettifiedDateTimeFormat = DateTimeFormatter.ofPattern("d MMM yy h:mma");
    /** Start date and time of the event. */
    private LocalDateTime startDateTime;
    /** End date and time of the event. */
    private LocalDateTime endDateTime;

    /**
     * Creates an event task with the input description and start and end date times.
     * 
     * @param description describes the nature of the event.
     * @param startDateTime the start date and time of the event. 
     * @param endDateTime the end date and time of the event.
     * @throws InvalidTaskException when any of description, start/end date and time are blank.
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) throws InvalidTaskException {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        validate();
    }

    /**
     * Creates an event from the input string array.
     * 
     * @param input string array containing description, isDone, event start and end date/times.
     * @throws InvalidTaskException when any of description, event start and end date/times are blank.
     */
    public Event(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
        startDateTime = LocalDateTime.parse(input[3], eventDateTimeFormat);
        endDateTime = LocalDateTime.parse(input[4], eventDateTimeFormat);
        validate();
    }
 
    /**
     * Validates that the created event object has acceptable parameters.
     * 
     * @throws InvalidTaskException when the event has unacceptable parameters.
     */
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

    /**
     * Returns the start date/time in string format.
     * 
     * @return the start date/time in string format.
     */
    public String getStringifiedStartDateTime() {
        return startDateTime.format(eventDateTimeFormat);
    }

    /**
     * Returns the end date/time in string format.
     * 
     * @return the end date/time in string format.
     */
    public String getStringifiedEndDateTime() {
        return endDateTime.format(eventDateTimeFormat);
    }

    // TODO: display "date startTime-endTime" if same day, else "startDateTime - endDateTime"
    /**
     * Returns a nicely formatted string that displays the status and details of an event.
     * 
     * @return a nicely formatted string that displays the status and details of an event.
     */
    public String getInfo() {
        return "[E]" + super.getInfo() + "(at: " + getPrettyStartDateTime() + "-" + getPrettyEndDateTime() + ")";
    }

    /**
     * Returns the start date/time in a prettified string format.
     * 
     * @return the start date/time in a prettified string format.
     */
    public String getPrettyStartDateTime() {
        return startDateTime.format(prettifiedDateTimeFormat);
    }

    /**
     * Returns the end date/time in a prettified string format.
     * 
     * @return the end date/time in a prettified string format.
     */
    public String getPrettyEndDateTime() {
        return endDateTime.format(prettifiedDateTimeFormat);
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
