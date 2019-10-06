package duke;

import duke.exception.InvalidTaskException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task addTodo(String description) throws InvalidTaskException {
        Todo todo = new Todo(description);
        tasks.add(todo);
        return todo;
    }

    public Task addDeadline(String description, LocalDateTime dueDate) throws InvalidTaskException {
        Deadline deadline = new Deadline(description, dueDate);
        tasks.add(deadline);
        return deadline;
    }

    public Task addEvent(String description, LocalDateTime startDateTime, LocalDateTime endDateTime)
            throws InvalidTaskException {
        Event event = new Event(description, startDateTime, endDateTime);
        tasks.add(event);
        return event;
    }

    public Task deleteTask(Integer index) {
        Task deletedTask = getTask(index);
        tasks.remove(index);
        return deletedTask;
    }

    public Task markTaskAsDone(Integer index) {
        getTask(index).markAsDone();
        return getTask(index);
    }

    public Task getTask(Integer index) {
        return tasks.get(index - 1);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> findMatching(String searchParams) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getInfo().contains(searchParams)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
