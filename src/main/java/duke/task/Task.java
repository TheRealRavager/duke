package duke.task;

import duke.exception.InvalidTaskException;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws InvalidTaskException {
        validateDescriptionNotBlank(description);
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }

    public String getInfo() {
        return "[" + getStatusIcon() + "] " + description;
    }

    public void markAsDone() {
        isDone = true;
    }

    private void validateDescriptionNotBlank(String description) throws InvalidTaskException {
        if (description.isEmpty()) {
            // TODO: replace 'task' with the specific type of task, e.g. Todo
            throw new InvalidTaskException("Task description cannot be blank!");
        }
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
