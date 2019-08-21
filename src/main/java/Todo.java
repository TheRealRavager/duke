public class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    public String getInfo() {
        return "[T]" + super.getInfo();
    }
}