package duke;

import duke.exception.InvalidTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    /** Formatter used to parse input due dates. */
    public static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");

    /**
     * Parses user input into Commands for execution.
     * @param userInput to be parsed.
     * @return a Command detailing what is to be done next.
     * @throws InvalidTaskException when the parsed input do not match any Command.
     */
    public Command parse(String userInput) throws InvalidTaskException {
        String[] splitInput = userInput.split(" ", 2);
        assert (splitInput.length == 2);
        try {
            CommandType commandType = CommandType.valueOf(CommandType.class, splitInput[0].toUpperCase());
            switch (commandType) {
            // `bye`
            case BYE:
                return new ByeCommand();
            // `list`
            case LIST:
                return new ListCommand();
            // `done task_number`
            case DONE:
                try {
                    Integer index = Integer.parseInt(splitInput[1]);
                    return new DoneCommand(index);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new InvalidTaskException("Invalid input! Done has the following format: `done task_number`");
                }
                // `todo task_description`
            case TODO:
                try {
                    String description = splitInput[1];
                    return new TodoCommand(description);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InvalidTaskException("Invalid input! Todo has the following format:"
                            + "`todo task_description`");
                }
                // `deadline task_description /by dueDate` DateTimeFormat: dd/MM/yyyy HHmm
            case DEADLINE:
                try {
                    String[] descAndDate = splitInput[1].split(" /by ", 2);
                    String description = descAndDate[0];
                    LocalDateTime dueDate = LocalDateTime.parse(descAndDate[1], DATEFORMAT);
                    return new DeadlineCommand(description, dueDate);
                } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                    throw new InvalidTaskException("Invalid input! Deadline has the following format:"
                            + "`deadline task_description /by dueDate` DateTimeFormat: dd/MM/yyyy HHmm");
                }
                // `event event_description /at startDateTime - endDateTime` DateTimeFormat: dd/MM/yyyy HHmm
            case EVENT:
                try {
                    String[] descAndDates = splitInput[1].split(" /at ", 2);
                    String[] startEndDates = descAndDates[1].split(" - ", 2);
                    String description = descAndDates[0];
                    LocalDateTime startDateTime = LocalDateTime.parse(startEndDates[0], DATEFORMAT);
                    LocalDateTime endDateTime = LocalDateTime.parse(startEndDates[1], DATEFORMAT);
                    return new EventCommand(description, startDateTime, endDateTime);
                } catch (ArrayIndexOutOfBoundsException | DateTimeParseException e) {
                    throw new InvalidTaskException("Invalid input! Event has the following format:"
                            + "`event event_description /at startDateTime"
                            + " - endDateTime` DateTimeFormat: dd/MM/yyyy HHmm");
                }
                // `delete task_number`
            case DELETE:
                try {
                    Integer index = Integer.parseInt(splitInput[1]);
                    return new DeleteCommand(index);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new InvalidTaskException("Invalid input! Delete has the following format:"
                            + "`delete task_number`");
                }
                // `find search_params`
            case FIND:
                try {
                    String searchParams = splitInput[1];
                    return new FindCommand(searchParams);
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new InvalidTaskException("Invalid input! Find has the following format:"
                            + "`find search_params`");
                }
            // `help`
            case HELP:
                return new HelpCommand();
            default:
                // TODO: have this string be based on enum values
                String commands = "BYE, LIST, DONE, TODO, DEADLINE, EVENT, DELETE, FIND";
                throw new InvalidTaskException("Invalid input! The available commands are: " + commands);
            }
        } catch (IllegalArgumentException e) {
            // TODO: have this string be based on enum values
            String commands = "BYE, LIST, DONE, TODO, DEADLINE, EVENT, DELETE, FIND";
            throw new InvalidTaskException("Invalid input! The available commands are: " + commands);
        }
    }
}
