package duke.task;

import duke.exception.InvalidTaskException;

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>(100);

    public void createTask(String[] params) throws InvalidTaskException {
        String taskCode = params[0];
        switch (taskCode) {
        case "T":
            tasks.add(createTodo(params));
        case "D":
            tasks.add(createDeadline(params));
        case "E":
            tasks.add(createEvent(params));
        }
    }

    private Todo createTodo(String[] params) throws InvalidTaskException {
        int count = 0;
        try {
            Boolean isDone = params[count += 1].equals("1");
            String description = params[count += 1];
            return new Todo(description, isDone);
        }
        catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("Missing todo description!");
        }
    }

    private Deadline createDeadline(String[] params) throws InvalidTaskException {
        int count = 0;
        try {
            Boolean isDone = params[count += 1].equals("1");
            String description = params[count += 1];
            LocalDateTime dueDate = LocalDateTime.parse(params[count += 1], Deadline.dueDateFormat);
            return new Deadline(description, isDone, dueDate);
        }
        catch (IndexOutOfBoundsException e) {
            String errorMessage = count == 2
                    ? "Missing deadline description and due date!" : "Missing deadline due date!";
            throw new IndexOutOfBoundsException(errorMessage);
        }
        catch (DateTimeParseException e) {
            throw new DateTimeParseException("Wrong date format!", e.getParsedString(), e.getErrorIndex());
        }
    }

    private Event createEvent(String[] params) throws InvalidTaskException {
        int count = 0;
        try {
            Boolean isDone = params[count += 1].equals("1");
            String description = params[count += 1];
            LocalDateTime startDateTime = LocalDateTime.parse(params[count += 1], Event.eventDateTimeFormat);
            LocalDateTime endDateTime = LocalDateTime.parse(params[count += 1], Event.eventDateTimeFormat);
            return new Event(description, isDone, startDateTime, endDateTime);
        }
        catch (IndexOutOfBoundsException e) {
            String errorMessage = count == 2
                    ? "Missing event description and dates/times!" : "Missing event dates/times!";
            throw new IndexOutOfBoundsException(errorMessage);
        }
        catch (DateTimeParseException e) {
            throw new DateTimeParseException("Wrong date format!", e.getParsedString(), e.getErrorIndex());
        }
    }

    public void markTaskAsDone(int index) {
        tasks.get(index - 1).markAsDone();
    }

    public void deleteTask(int index) {
        tasks.remove(index - 1);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int index) {
        return tasks.get(index - 1);
    }

    public int size() {
        return tasks.size();
    }
}
