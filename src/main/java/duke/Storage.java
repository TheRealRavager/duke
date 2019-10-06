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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Storage class is responsible for reading/writing tasks from/to a save file.
 */
public class Storage {
    // Tasks saved in following format: taskType|isDone|description|otherFields
    /** Directory of saved tasks. */
    private final String SAVE_DIRECTORY = "./data/duke.txt";

    /**
     * Loads tasks from the save file into an arraylist and returns it.
     * 
     * @return an arraylist containing the saved tasks.
     */
    public ArrayList<Task> load() throws InvalidTaskException {
        return load(SAVE_DIRECTORY);
    }

    public ArrayList<Task> load(String saveDirectory) throws InvalidTaskException {
        ArrayList<Task> tasks = new ArrayList<>(100);
        try {
            System.out.println("Loading save file from: " + saveDirectory + " ...");
            if (Files.notExists(Paths.get(saveDirectory))) {
                System.out.println("Save file missing! Attempting to create new file...");
            }
            File savedTasks = new File(saveDirectory);
            Scanner sc = new Scanner(savedTasks);
            while (sc.hasNext()) {
                try {
                    Task task = parseFileToDuke(sc.nextLine());
                    tasks.add(task);
                } catch (InvalidTaskException e) {
                    throw new InvalidTaskException("Save file corrupted!");
                }
            }
            System.out.println("File load successful.");
            sc.close();
        } catch (FileNotFoundException e) {
            try {
                FileWriter fw = new FileWriter(saveDirectory);
                fw.write("");
                fw.close();
            } catch (IOException er) {
                throw new InvalidTaskException("Unable to access save directory! Aborting!");
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
        save(tasks, SAVE_DIRECTORY);
    }

    public void save(ArrayList<Task> tasks, String saveDirectory) {
        try {
            FileWriter fw = new FileWriter(saveDirectory);
            String parsedTasks = parseDukeToFile(tasks);
            fw.write(parsedTasks);
            fw.close();
        } catch (IOException e) {
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
            throw new InvalidTaskException("Unrecognized task! Save file possibly corrupt!");
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
                parsedTask = "D|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|"
                        + ((Deadline) task).getStringifiedDueDate();
            } else if (task instanceof Event) {
                parsedTask = "E|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|"
                        + ((Event) task).getStringifiedStartDateTime() + "|"
                        + ((Event) task).getStringifiedEndDateTime();
            }
            parsedTasks += parsedTask + "\n";
        }
        return parsedTasks;
    }
}
