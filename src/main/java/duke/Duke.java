package duke;

import duke.Storage;

import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;

import duke.exception.InvalidTaskException;

import java.util.Scanner;
import java.util.ArrayList;

import java.time.LocalDateTime;

/**
 * Main class for Duke chatbot.
 * Handles user input.
 */
public class Duke {
    /** Handles read/write to save file. */
    private static Storage storageHandler = new Storage();
    /** List of current tasks. */
    private static ArrayList<Task> tasks = storageHandler.load();

    public static void main(String[] args) {
        greet();
        respondToInput();
    }

    /**
     * Sends a friendly message to the user.
     */
    private static void greet() {
        dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    /**
     * Responds to user input by determining which subsequent methods to call.
     */
    private static void respondToInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            try {
                if (userInput.equals("list")) {
                    displayList();
                } else if (checkIsInputEquals(userInput, "done ")) {
                    Task task = tasks.get(Integer.parseInt(userInput.substring("done ".length())) - 1);
                    task.markAsDone();
                    dukeReply("Successfully marked the following task as done:\n" + task.getInfo());
                } else if (checkIsInputEquals(userInput, "todo ")) {
                    addAndDisplayNewTodo(userInput);
                } else if (checkIsInputEquals(userInput, "deadline ")) {
                    addAndDisplayNewDeadline(userInput);
                } else if (checkIsInputEquals(userInput, "event ")) {
                    addAndDisplayNewEvent(userInput);
                } else if (checkIsInputEquals(userInput, "delete ")) {
                    int taskIndex = Integer.parseInt(userInput.substring("delete ".length())) - 1;
                    deleteAndDisplayTask(taskIndex);
                } else {
                    dukeReply("I don't know what that means, sorry!");
                }
            }
            catch (InvalidTaskException e) {
                dukeReply("Oops sorry you're missing some stuff!!\n" + e.getMessage());
            }
            catch (ArrayIndexOutOfBoundsException e) {
                dukeReply("I didnt understand that, sorry!");
            }
            finally {
                userInput = sc.nextLine();
            }
        }
        dukeReply("Till next time, goodbye!");
        sc.close();
    }

    /**
     * Returns true if the first substring of the input string is equal to the comparison string.
     * 
     * @param input is the input string being checked.
     * @param comparisonString is the string being matched.
     * @return true if the first substring of the input string is equal to the comparison string.
     */
    private static boolean checkIsInputEquals(String input, String comparisonString) {
        if (input.length() < comparisonString.length()) {
            return false;
        } else {
            return input.substring(0, comparisonString.length()).equals(comparisonString);
        }
    }

    /**
     * Displays the contents of current task list.
     */
    private static void displayList() {
        String finalOutput = "";
        boolean first = true;
        for (int i = 0; i < tasks.size(); i++) {
            if (!first) {
                finalOutput += "\n";
            }
            first = false;
            finalOutput += i + 1 + ". " + tasks.get(i).getInfo();
        }
        dukeReply("Here are the tasks in your list:\n" + finalOutput);
    }

    /**
     * Displays the input task.
     * 
     * @param task the input task.
     */
    private static void displayAddedTask(Task task) {
        dukeReply("Got it. I've added this task:\n  " + task.getInfo() + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Adds and subsequently displays a new todo created from the user input.
     * 
     * @param userInput string representing the todo the user intends to create.
     * @throws InvalidTaskException when there are insufficient or incorrect parameters to initialize a todo.
     */
    private static void addAndDisplayNewTodo(String userInput) throws InvalidTaskException {
        Todo newTodo = new Todo(userInput.substring("todo ".length()));
        tasks.add(newTodo);
        storageHandler.save(tasks);
        displayAddedTask(newTodo);
    }

    /**
     * Adds and subsequently displays a new deadline created from the user input.
     * 
     * @param userInput string representing the deadline the user intends to create.
     * @throws InvalidTaskException when there are insufficient or incorrect parameters to initialize a deadline.
     */
    private static void addAndDisplayNewDeadline(String userInput) throws InvalidTaskException {
        String[] descriptionAndDate = userInput.substring("deadline ".length()).split("/by ", 2);
        String description = descriptionAndDate[0];
        LocalDateTime dueDate = LocalDateTime.parse(descriptionAndDate[1].trim(), Deadline.dueDateFormat);
        Deadline newDeadline = new Deadline(description, dueDate);
        tasks.add(newDeadline);
        storageHandler.save(tasks);
        displayAddedTask(newDeadline);
    }

    /**
     * Adds and subsequently displays a new event created from the user input.
     * 
     * @param userInput string representing the event the user intends to create.
     * @throws InvalidTaskException when there are insufficient or incorrect parameters to initialize a event.
     */
    private static void addAndDisplayNewEvent(String userInput) throws InvalidTaskException {
        String[] descriptionAndDateTimes = userInput.substring("event ".length()).split("/at ", 2);
        String[] startAndEndDateTimes = descriptionAndDateTimes[1].split("-", 2);
        String description = descriptionAndDateTimes[0];
        LocalDateTime startDateTime = LocalDateTime.parse(startAndEndDateTimes[0].trim(), Event.eventDateTimeFormat);
        LocalDateTime endDateTime = LocalDateTime.parse(startAndEndDateTimes[1].trim(), Event.eventDateTimeFormat);

        Event newEvent = new Event(description, startDateTime, endDateTime);
        tasks.add(newEvent);
        storageHandler.save(tasks);
        displayAddedTask(newEvent);
    }

    /**
     * Deletes and subsquently displays the task at the input task index.
     * 
     * @param taskIndex task index of the task to be deleted.
     */
    private static void deleteAndDisplayTask(int taskIndex) {
        Task task = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        storageHandler.save(tasks);
        dukeReply("I have removed the following task:\n  " + task + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    /**
     * Sends the input reply string to the user after formatting it.
     * 
     * @param reply input string to be formatted.
     */
    private static void dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        System.out.println(enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine);
    }
}
