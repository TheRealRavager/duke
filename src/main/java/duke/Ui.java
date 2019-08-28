package duke;

import java.util.List;

import duke.task.Task;
import duke.task.TaskList;

public class Ui {

    public void greet() {
        dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    public void sayGoodbye() {
        dukeReply("Goodbye! Till next time!");
    }

    public void displayList(List<Task> list) {
        String finalOutput = "";
        boolean first = true;
        for (int i = 0; i < list.size(); i++) {
            if (!first) {
                finalOutput += "\n";
            }
            first = false;
            finalOutput += i + 1 + ". " + list.get(i).getInfo();
        }
        if (finalOutput.isBlank()) {
            dukeReply("Your task list is empty!");
        } else {
            dukeReply("Here are the tasks in your list:\n" + finalOutput);
        }
    }

    public void displayAddedTask(Task task, TaskList tasks) {
        dukeReply("Got it. I've added this task:\n  " + task.getInfo() + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void addAndDisplayNewTodo(String userInput) throws InvalidTaskException {
        Todo newTodo = new Todo(userInput.substring("todo ".length()));
        tasks.add(newTodo);
        storageHandler.save(tasks);
        displayAddedTask(newTodo);
    }

    // // TODO: add ability to detect improper datetime inputs
    // private static void addAndDisplayNewDeadline(String userInput) throws InvalidTaskException {
    //     String[] descriptionAndDate = userInput.substring("deadline ".length()).split("/by ", 2);
    //     String description = descriptionAndDate[0];
    //     LocalDateTime dueDate = LocalDateTime.parse(descriptionAndDate[1].trim(), Deadline.dueDateFormat);
    //     Deadline newDeadline = new Deadline(description, dueDate);
    //     tasks.add(newDeadline);
    //     storageHandler.save(tasks);
    //     displayAddedTask(newDeadline);
    // }

    // private static void addAndDisplayNewEvent(String userInput) throws InvalidTaskException {
    //     String[] descriptionAndDateTimes = userInput.substring("event ".length()).split("/at ", 2);
    //     String[] startAndEndDateTimes = descriptionAndDateTimes[1].split("-", 2);
    //     String description = descriptionAndDateTimes[0];
    //     LocalDateTime startDateTime = LocalDateTime.parse(startAndEndDateTimes[0].trim(), Event.eventDateTimeFormat);
    //     LocalDateTime endDateTime = LocalDateTime.parse(startAndEndDateTimes[1].trim(), Event.eventDateTimeFormat);

    //     Event newEvent = new Event(description, startDateTime, endDateTime);
    //     tasks.add(newEvent);
    //     storageHandler.save(tasks);
    //     displayAddedTask(newEvent);
    // }
    // private static void displayDeletion(int taskIndex) {
    //     Task task = tasks.get(taskIndex);
    //     tasks.remove(taskIndex);
    //     storageHandler.save(tasks);
    //     dukeReply("I have removed the following task:\n  " + task + "\nNow you have " + tasks.size() + " tasks in the list.");
    // }

    public void dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        System.out.println(enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine);
    }

    
}