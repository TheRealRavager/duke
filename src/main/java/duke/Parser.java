package duke;

import java.util.Arrays;

import duke.exception.InvalidUserInputException;

public class Parser {
    private String[] validCommands = {"todo, deadline, event, list, bye, delete, done"};

    // Parse user input
    public String[] parseUserInput(String userInput) throws InvalidUserInputException {
        String[] commandAndParams = userInput.split(" ", 2);
        String command = commandAndParams[0].toLowerCase();
        if (!Arrays.asList(validCommands).contains(command)) {
            throw new InvalidUserInputException("Sorry I didn't recognize that command");
        } else {
            String params = commandAndParams[1];
            switch (command) {
            case "todo":
                return parseTodo(params);
            case "deadline":
                return parseDeadline(params);
            case "event":
                return parseEvent(params);
            default:
                // list, bye, delete, done commands
                return commandAndParams;
            }
        }
    }

    private String[] parseTodo(String params) {
        String[] todoParams = {"T", params};
        return todoParams;
    }

    private String[] parseDeadline(String params) {
        String[] descriptionAndDeadline = params.split("/by ", 2);
        String description = descriptionAndDeadline[0];
        String deadline = descriptionAndDeadline[1];
        String[] deadlineParams = {"D", "0", description, deadline};
        return deadlineParams;
    }

    private String[] parseEvent(String params) {
        String[] descriptionAndDateTimes  = params.split("/at ", 2);
        String[] startAndEndDateTimes = descriptionAndDateTimes[1].split("-", 2);
        String description = descriptionAndDateTimes[0];
        String startDateTime = startAndEndDateTimes[0];
        String endDateTime = startAndEndDateTimes[1];
        String[] eventParams = {"E", "0", description, startDateTime, endDateTime};
        return eventParams;
    }

    // Parse from storage format to duke processing format and vice versa

    public String[] parseStoredLineToTaskParams(String line) {
        String[] taskParams = line.split("\\|");
        return taskParams;
    }

    public String parseTaskParamsToStoredLine(String[] taskParams) {
        String storedLine = "";
        for (String param : taskParams) {
            storedLine += "|" + param;
        }
        return storedLine;
    }
}