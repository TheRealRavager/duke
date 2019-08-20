import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    private static ArrayList<String> storedInputs = new ArrayList<>(100);
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
        while (!userInput.toLowerCase().equals("bye")) {
            if (userInput.equals("list")) {
                int n = 1;
                String finalOutput = "";
                boolean first = true;
                for (String storedInput : storedInputs) {
                    // May require refactor if performance is undesirably poor due to many string concatenations (review at towards end of project)
                    if (!first) {
                        finalOutput += "\n";
                    }
                    first = false;
                    finalOutput += n + ". " + storedInput;
                    n += 1;
                }
                dukeReply(finalOutput);
            } else {
                storedInputs.add(userInput);
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
