public class Deadline extends Task {
    private String dueDate;

    public Deadline(String description, String dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    public String getInfo() {
        return "[D]" + super.getInfo() + "(by: " + dueDate + ")";
    }

    public String getDueDate() {
        return dueDate;
    }
}