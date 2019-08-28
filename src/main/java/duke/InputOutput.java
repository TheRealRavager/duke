package duke;

import duke.task.Task;
import duke.task.Todo;
import duke.exception.InvalidTaskException;
import duke.task.Deadline;
import duke.task.Event;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class InputOutput {
    // save tasks as:
    // taskType|isDone|description|otherFields
    private final String SAVE_DIRECTORY = "../../../data/duke.txt";

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

    private String parseDukeToFile(ArrayList<Task> tasks) {
        // TODO: may cause ordering issues
        // TODO: find better way to identify tasks
        String parsedTasks = "";
        for (Task task : tasks) {
            String parsedTask = "";
            if (task instanceof Todo) {
                parsedTask = "T|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription();
            } else if (task instanceof Deadline) {
                parsedTask = "D|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Deadline) task).getDueDate();
            } else if (task instanceof Event) {
                parsedTask = "E|" + (task.getIsDone() ? "1|" : "0|") + task.getDescription() + "|" + ((Event) task).getStartDateTime() + "|" + ((Event) task).getEndDateTime();
            }
            parsedTasks += "\n" + parsedTask;
        }
        return parsedTasks;
    }
}

// TODO: Stores codes for tasks i.e T = todo, D = deadline, E = event etc
enum taskCode {

}
