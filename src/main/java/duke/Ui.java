package duke;

import duke.task.Task;
import java.util.ArrayList;

public class Ui {
    /**
     * Sends a friendly message to the user.
     */
    public String greet() {
        return dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    /**
     * Displays the input task.
     *
     * @param task the input task.
     */
    public String displayAddedTask(Task task, ArrayList<Task> tasks) {
        return dukeReply("Got it. I've added this task:\n  " + task.getInfo() + "\nNow you have "
                + tasks.size() + " tasks in the list.");
    }

    public String displayDeletedTask(Task task, ArrayList<Task> tasks) {
        return dukeReply("I have removed the following task:\n  " + task + "\nNow you have "
                + tasks.size() + " tasks in the list.");
    }

    /**
     * Displays the contents of current task list.
     */
    public String displayList(ArrayList<Task> tasks) {
        String finalOutput = "";
        boolean first = true;
        for (int i = 0; i < tasks.size(); i++) {
            if (!first) {
                finalOutput += "\n";
            }
            first = false;
            finalOutput += i + 1 + ". " + tasks.get(i).getInfo();
        }
        return dukeReply("Here are the tasks in your list:\n" + finalOutput);
    }

    public String doneReply(Task task) {
        return dukeReply("Successfully marked the following task as done:\n" + task.getInfo());
    }

    public String displayErrors(Exception e) {
        return dukeReply(e.getMessage());
    }

    public String sayGoodbye() {
        return dukeReply("Till next time, goodbye!");
    }

    /**
     * Sends the input reply string to the user after formatting it.
     *
     * @param reply input string to be formatted.
     */
    private String dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        return enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine;
    }
}
