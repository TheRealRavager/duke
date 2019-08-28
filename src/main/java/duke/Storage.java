package duke;

import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.exception.InvalidTaskException;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage class is responsible for reading/writing tasks from/to a save file.
 */
public class Storage {
    // Tasks saved in following format: taskType|isDone|description|otherFields
    /** Directory of saved tasks. */
    private final String SAVE_DIRECTORY = "../../../data/duke.txt";

    /**
     * Loads tasks from the save file into an arraylist and returns it.
     * 
     * @return an arraylist containing the saved tasks.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>(100);
        try {
            File savedTasks = new File(SAVE_DIRECTORY);
            Scanner sc = new Scanner(savedTasks);
            while (sc.hasNext()) {
                try {
                    Task task = parseFileToDuke(sc.nextLine());
                    tasks.add(task);
                }
                // TODO: Should handle "corrupted" saves in a different way.
                catch (InvalidTaskException e) {
                    continue;
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("No save file found! Creating one!");
            try {
                FileWriter fw = new FileWriter(SAVE_DIRECTORY);
                fw.write("");
                fw.close();
            }
            catch (IOException er) {
                // TODO: make it actually abort
                System.out.println("Unable to access save directory! Aborting!");
            }
        }
        return tasks;
    }

    /**
     * Writes the input task list to the save file directory.
     * 
     * @param tasks input task list containing all the current tasks.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(SAVE_DIRECTORY);
            String parsedTasks = parseDukeToFile(tasks);
            fw.write(parsedTasks);
            fw.close();
        }
        catch (IOException e) {
            // TODO: Handle exception properly
            System.out.println("Failed to save changes");
        }
    }

    /**
     * Parses the input lines to create tasks.
     * 
     * @param line input line from the save file.
     * @return a task created from the input line.
     * @throws InvalidTaskException when the task type is unrecognized.
     */
    private Task parseFileToDuke(String line) throws InvalidTaskException {
        String[] taskDetails = line.split("\\|");
        String taskType = taskDetails[0];
        Task task = null;

        switch (taskType) {
        case "T":
            task = new Todo(taskDetails);
            break;
        case "D":
            task = new Deadline(taskDetails);
            break;
        case "E":
            task = new Event(taskDetails);
            break;
        default:
            // TODO: Should change this to diff type of exception
            throw new InvalidTaskException("Unrecognized task!");
        }
        return task;
    }

    /**
     * Parses the input list of tasks into a single string containing all the tasks.
     * 
     * @param tasks input list of tasks to be parsed.
     * @return a single string containing all the tasks.
     */
    private String parseDukeToFile(ArrayList<Task> tasks) {
        String parsedTasks = "";
        for (Task task : tasks) {
            String parsedTask = "";
            if (task instanceof Todo) {
                parsedTask = "T|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription();
            } else if (task instanceof Deadline) {
                parsedTask = "D|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Deadline) task).getStringifiedDueDate();
            } else if (task instanceof Event) {
                parsedTask = "E|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Event) task).getStringifiedStartDateTime() + "|" + ((Event) task).getStringifiedEndDateTime();
            }
            parsedTasks += "\n" + parsedTask;
        }
        return parsedTasks;
    }
}
