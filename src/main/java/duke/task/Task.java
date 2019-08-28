package duke.task;

import duke.exception.InvalidTaskException;

/**
 * Abstract class emulating a task to be performed.
 */
public abstract class Task {
    /** Description that summarizes the details of the task. */
    protected String description;
    /** Boolean value that represents whether a task has been completed. */
    protected boolean isDone;

    /**
     * Creates a new task object that is not yet done, with the input description.
     * 
     * @param description short summary of the details of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Validates that the created Task object has acceptable parameters.
     * 
     * @throws InvalidTaskException when the Task has unacceptable parameters.
     */
    protected abstract void validate() throws InvalidTaskException;

    // Getter/setters

    /**
     * Returns the description of the task.
     * 
     * @return the description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns true if the task is done and false otherwise.
     * 
     * @return true if the task is done and false otherwise.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Returns a string containing a tick symbol if the task is done and a cross otherwise.
     * 
     * @return a string containing a tick symbol if the task is done and a cross otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    /**
     * Returns a nicely formatted string that displays the status and description of a task.
     * 
     * @return a nicely formatted string that displays the status and description of a task.
     */
    public String getInfo() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Marks the current task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
