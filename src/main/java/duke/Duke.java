package duke;

import duke.InputOutput;

import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;

import duke.exception.InvalidTaskException;

import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static InputOutput storageHandler = new InputOutput();
    private static ArrayList<Task> tasks = storageHandler.load();
    public static void main(String[] args) {
        greet();
        respondToInput();
    }

    private static void greet() {
        dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    // TODO: Create input parser to handle all this!
    // TODO: Create "command" enum
    private static void respondToInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        // May require refactor if performance is undesirably poor due to many string concatenations (review at towards end of project)
        // Should refactor by extract method
        while (!userInput.equals("bye")) {
            try {
                // TODO: if list is empty, print out a different message
                // note: below methods violate SRP (responsible for action + printing). To refactor in future.
                if (userInput.equals("list")) {
                    displayList();
                } else if (checkIsInputEquals(userInput, "done ")) {
                    // TODO: handle out of bounds exception (only 3 task but try to mark 4th as done)
                    // TODO: handle invalid input exception (non-integer after "done")
                    Task task = tasks.get(Integer.parseInt(userInput.substring("done ".length())) - 1);
                    task.markAsDone();
                    dukeReply("Successfully marked the following task as done:\n" + task.getInfo());
                } else if (checkIsInputEquals(userInput, "todo ")) {
                    // TODO: handle exception cause the add and displays will throw oob exception methinks
                    addAndDisplayNewTodo(userInput);
                } else if (checkIsInputEquals(userInput, "deadline ")) {
                    // TODO: same here
                    addAndDisplayNewDeadline(userInput);
                } else if (checkIsInputEquals(userInput, "event ")) {
                    // TODO: and here
                    addAndDisplayNewEvent(userInput);
                // Will throw error if wrong input, need to manage error better
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

    private static boolean checkIsInputEquals(String input, String comparisonString) {
        if (input.length() < comparisonString.length()) {
            return false;
        } else {
            return input.substring(0, comparisonString.length()).equals(comparisonString);
        }
    }

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

    private static void displayAddedTask(Task task) {
        dukeReply("Got it. I've added this task:\n  " + task.getInfo() + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void addAndDisplayNewTodo(String userInput) throws InvalidTaskException {
        Todo newTodo = new Todo(userInput.substring("todo ".length()));
        tasks.add(newTodo);
        storageHandler.save(tasks);
        displayAddedTask(newTodo);
    }

    private static void addAndDisplayNewDeadline(String userInput) throws InvalidTaskException {
        String[] descriptionAndDate = userInput.substring("deadline ".length()).split("/by ", 2);
        String description = descriptionAndDate[0];
        String dueDate = descriptionAndDate[1];
        Deadline newDeadline = new Deadline(descriptionAndDate[0], descriptionAndDate[1]);
        tasks.add(newDeadline);
        storageHandler.save(tasks);
        displayAddedTask(newDeadline);
    }

    private static void addAndDisplayNewEvent(String userInput) throws InvalidTaskException {
        String[] descriptionAndDateTimes = userInput.substring("event ".length()).split("/at ", 2);
        String[] startAndEndDateTimes = descriptionAndDateTimes[1].split("-", 2);
        Event newEvent = new Event(descriptionAndDateTimes[0], startAndEndDateTimes[0], startAndEndDateTimes[1]);
        tasks.add(newEvent);
        storageHandler.save(tasks);
        displayAddedTask(newEvent);
    }

    private static void deleteAndDisplayTask(int taskIndex) {
        Task task = tasks.get(taskIndex);
        tasks.remove(taskIndex);
        storageHandler.save(tasks);
        dukeReply("I have removed the following task:\n  " + task + "\nNow you have " + tasks.size() + " tasks in the list.");
    }

    private static void dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        System.out.println(enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine);
    }
}
