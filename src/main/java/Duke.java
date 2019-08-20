import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        greet();
        echoIfInputNotBye();
    }

    private static void greet() {
        dukeReply("Hello! My name is Duke!\nHow may I help you?");
    }

    private static void echoIfInputNotBye() {
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        while (!userInput.equals("bye")) {
            dukeReply(userInput);
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
