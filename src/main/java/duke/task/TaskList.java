package duke.task;

import duke.exception.InvalidTaskException;

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class TaskList {
    private ArrayList<Task> tasks = new ArrayList<>(100);
    // Task codes representing each type of task
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}






    // private Task parseFileLineToDukeFormat(String line) throws InvalidTaskException {
    //     String[] taskDetails = line.split("\\|");
    //     String taskType = taskDetails[0];
    //     Task task = null;

    //     switch (taskType) {
    //     case "T":
    //         task = new Todo(taskDetails);
    //         break;
    //     case "D":
    //         task = new Deadline(taskDetails);
    //         break;
    //     case "E":
    //         task = new Event(taskDetails);
    //         break;
    //     default:
    //         // TODO: Should change this to diff type of exception
    //         throw new InvalidTaskException("Unrecognized task!");
    //     }
    //     return task;
    // }

    // private String parseDukeLineToFileFormat(ArrayList<Task> tasks) {
    //     // TODO: may cause ordering issues
    //     // TODO: find better way to identify tasks
    //     String parsedTasks = "";
    //     for (Task task : tasks) {
    //         String parsedTask = "";
    //         if (task instanceof Todo) {
    //             parsedTask = "T|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription();
    //         } else if (task instanceof Deadline) {
    //             parsedTask = "D|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Deadline) task).getStringifiedDueDate();
    //         } else if (task instanceof Event) {
    //             parsedTask = "E|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Event) task).getStringifiedStartDateTime() + "|" + ((Event) task).getStringifiedEndDateTime();
    //         }
    //         parsedTasks += "\n" + parsedTask;
    //     }
    //     return parsedTasks;
    // }