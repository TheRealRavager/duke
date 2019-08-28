package duke;

import duke.task.TaskList;
import duke.exception.InvalidTaskException;
import duke.exception.InvalidUserInputException;

import java.util.Scanner;

public class Duke {
    private Ui ui;
    private Storage storage;
    private Parser parser;
    private TaskList taskList;

    public Duke() {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage();
        taskList = new TaskList();
    }

    public void initialise() {
        String[] storedTasks = storage.load().split("\n");
        for (String task : storedTasks) {
            try {
                String[] taskParams = parser.parseStoredTaskToTaskParams(task);
                taskList.createTask(taskParams);
            }
            catch (InvalidTaskException e) {
                ui.dukeReply("Invalid task detected, deleting...");
            }
        }
    }

    public void run() {
        initialise();
        ui.greet();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            try {
                // TODO: This section can be made better by implementing a command class, but I am out of time.
                String[] parsedInput = parser.parseUserInput(sc.nextLine());
                switch (parsedInput[0]) {
                case "list":
                    ui.displayList(taskList.getTasks());
                    break;
                case "bye":
                    throw new InterruptedException();
                case "delete":
                    int index = Integer.parseInt(parsedInput[1]);
                    Task deletedTask = taskList.getTask(index);
                    taskList.deleteTask(index);
                    storage.save(taskList.getTasks());
                    break;
                case "done":
                    taskList.markTaskAsDone(Integer.parseInt(parsedInput[1]));
                    break;
                default:
                    taskList.createTask(parsedInput);
                    ui.displayAddedTask(task, tasks);
                }
            }
            catch (InvalidUserInputException e) {
                ui.dukeReply(e.getMessage());
            }
            catch (InvalidTaskException e) {
                ui.dukeReply(e.getMessage());
            }
            catch (InterruptedException e) {
                ui.sayGoodbye();
                break;
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Duke().run();
    }

}
