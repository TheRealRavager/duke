package duke;

import duke.exception.InvalidTaskException;
import duke.task.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Executor {
    private Ui ui;
    private Storage storageHandler;
    private TaskList tasks;
    private Parser parser;


    /**
     * Creates an executor which handles the user input.
     * @param ui the component that drives ui logic.
     * @param storageHandler the component responsible for task read and write to file.
     * @param tasks the component responsible for managing tasks.
     * @param parser the component responsible for parsing user input.
     */
    public Executor(Ui ui, Storage storageHandler, TaskList tasks, Parser parser) {
        this.ui = ui;
        this.storageHandler = storageHandler;
        this.tasks = tasks;
        this.parser = parser;
    }

    /**
     * Responds to user input by determining which subsequent methods to call.
     */
    public String execute(String userInput) {
        if (userInput.toUpperCase().equals("BYE")) {
            ui.sayGoodbye();
            System.exit(0);
        }
        String reply = null;
        try {
            Command userCommand = parser.parse(userInput);
            switch (userCommand.getType()) {
            case LIST:
                reply = ui.displayList(tasks.getTasks());
                break;
            case DONE:
                Integer index = ((DoneCommand) userCommand).getIndex();
                Task doneTask = tasks.markTaskAsDone(index);
                reply = ui.doneReply(doneTask);
                break;
            case TODO:
                Task addedTodo = tasks.addTodo(((TodoCommand) userCommand).getDescription());
                reply = ui.displayAddedTask(addedTodo, tasks.getTasks());
                break;
            case DEADLINE:
                Task addedDeadline = tasks.addDeadline(
                        ((DeadlineCommand) userCommand).getDescription(),
                        ((DeadlineCommand) userCommand).getDueDate());
                reply = ui.displayAddedTask(addedDeadline, tasks.getTasks());
                break;
            case EVENT:
                Task addedEvent = tasks.addEvent(
                        ((EventCommand) userCommand).getDescription(),
                        ((EventCommand) userCommand).getStartDateTime(),
                        ((EventCommand) userCommand).getEndDateTime()
                );
                reply = ui.displayAddedTask(addedEvent, tasks.getTasks());
                break;
            case DELETE:
                Task deletedTask = tasks.deleteTask(((DeleteCommand) userCommand).getIndex());
                reply = ui.displayDeletedTask(deletedTask, tasks.getTasks());
                break;
            case FIND:
                ArrayList<Task> matchingTasks = tasks.findMatching(((FindCommand) userCommand).getSearchParams());
                reply = ui.displayList(matchingTasks);
                break;
            case HELP:
                reply = ui.renderHelp();
                break;
            default:
                // TODO: have this string be based on enum values
                String commands = "BYE, LIST, DONE, TODO, DEADLINE, EVENT, DELETE, FIND";
                reply = "I don't know what that means! The available commands are: " + commands;
            }
        } catch (InvalidTaskException e) {
            reply = e.getMessage();
        } finally {
            storageHandler.save(tasks.getTasks());
            assert (reply != null);
            return reply;
        }
    }
}
