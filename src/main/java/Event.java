public class Event extends Task {
    private String startDateTime;
    private String endDateTime;

    public Event(String description, String startDateTime, String endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    // TODO: change to dateTime
    public String getInfo() {
        return "[E]" + super.getInfo() + "(at: " + startDateTime + "-" + endDateTime + ")";
    }
}