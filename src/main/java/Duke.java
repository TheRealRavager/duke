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
        while (!userInput.toLowerCase().equals("bye")) {
            if (userInput.equals("list")) {
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
            } else if (userInput.length() > 4 && userInput.substring(0, 5).equals("done ")) {
                Task task = tasks.get(Integer.parseInt(userInput.substring(5)) - 1);
                task.markAsDone();
                dukeReply("Successfully marked the following task as done:\n" + task.getInfo());
            } else {
                tasks.add(new Task(userInput));
                dukeReply("added: " + userInput);
            }
            userInput = sc.nextLine();
        }
        dukeReply("Till next time, goodbye!");
        sc.close();
    }

    private static void dukeReply(String reply) {
        String enclosingLine = "    ____________________________________________________________";
        String indentedReply = reply.replaceAll("\n", "\n     ");
        System.out.println(enclosingLine + "\n     " + indentedReply + "\n" + enclosingLine);
    }
}
