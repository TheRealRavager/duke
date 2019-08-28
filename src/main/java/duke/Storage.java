package duke;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

// Handles reading and writing from/to save file via strings
public class Storage {
    // save tasks as:
    // taskType|isDone|description|otherFields
    private final String SAVE_DIRECTORY = "../../../data/duke.txt";

    public String load() {
        String tasks = "";
        try {
            File savedTasks = new File(SAVE_DIRECTORY);
            Scanner sc = new Scanner(savedTasks);
            while (sc.hasNext()) {
                tasks += "\n" + sc.nextLine();
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("No existing save file found! Attempting to create one!");
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

    public void save(String tasks) {
        try {
            FileWriter fw = new FileWriter(SAVE_DIRECTORY);
            fw.write(tasks);
            fw.close();
        }
        catch (IOException e) {
            // TODO: Handle exception properly
            System.out.println("Failed to save changes");
        }
    }
}
