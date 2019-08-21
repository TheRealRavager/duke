import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static ArrayList<Task> tasks = new ArrayList<>(100);
    public static void main(String[] args) {
        greet();
        respondToInput();
    }

    private static void greet() {
        dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    private static void respondToInput() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        // May require refactor if performance is undesirably poor due to many string concatenations (review at towards end of project)
        // Should refactor by extract method
        while (!userInput.equals("bye")) {
            // TODO: if list is empty, print out a different message
            if (userInput.equals("list")) {
                displayList();
            } else if (checkIsInputEquals(userInput, "done ")) {
                Task task = tasks.get(Integer.parseInt(userInput.substring(5)) - 1);
                task.markAsDone();
                dukeReply("Successfully marked the following task as done:\n" + task.getInfo());
            } else if (checkIsInputEquals(userInput, "todo ")) {
                addAndDisplayNewTodo(userInput);
            } else if (checkIsInputEquals(userInput, "deadline ")) {
                addAndDisplayNewDeadline(userInput);
            } else if (checkIsInputEquals(userInput, "event ")) {
                addAndDisplayNewEvent(userInput);
            } else {
                // TODO: add error handling for invalid input
            }
            userInput = sc.nextLine();
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

    private static void addAndDisplayNewTodo(String userInput) {
        Todo newTodo = new Todo(userInput.substring("todo ".length()));
        tasks.add(newTodo);
        displayAddedTask(newTodo);
    }

    private static void addAndDisplayNewDeadline(String userInput) {
        String[] descriptionAndDate = userInput.substring("deadline ".length()).split("/by ");
        Deadline newDeadline = new Deadline(descriptionAndDate[0], descriptionAndDate[1]);
        tasks.add(newDeadline);
        displayAddedTask(newDeadline);
    }

    private static void addAndDisplayNewEvent(String userInput) {
        String[] descriptionAndDateTimes = userInput.substring("event ".length()).split("/at ");
        String[] startAndEndDateTimes = descriptionAndDateTimes[1].split("-");
        Event newEvent = new Event(descriptionAndDateTimes[0], startAndEndDateTimes[0], startAndEndDateTimes[1]);
        tasks.add(newEvent);
        displayAddedTask(newEvent);
    }

    private static void dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        System.out.println(enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine);
    }
}
