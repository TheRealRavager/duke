package duke.task;

import duke.exception.InvalidTaskException;

/**
 * Todo class emulates a task to be completed.
 */
public class Todo extends Task {
    /**
     * Creates a new todo object with the input description, that is not yet completed.
     * 
     * @param description a short summary of the task to be done.
     * @throws InvalidTaskException when description is blank.
     */
    public Todo(String description) throws InvalidTaskException {
        super(description);
        validate();
    }

    /**
     * Creates a new todo object with the input string array containing description and isDone.
     * 
     * @param input string array containing description and isDone.
     * @throws InvalidTaskException when description is blank.
     */
    public Todo(String[] input) throws InvalidTaskException {
        super(input[2]);
        isDone = input[1].equals("1");
        validate();
    }

    /**
     * Validates that the created todo object has acceptable parameters.
     * 
     * @throws InvalidTaskException when the todo has unacceptable parameters.
     */
    protected void validate() throws InvalidTaskException {
        if (description.isBlank()) {
            throw new InvalidTaskException("Description cannot be blank");
        }
    }

    // Getters/setters

    /**
     * Returns a nicely formatted string that displays the status and details of a todo.
     * 
     * @return a nicely formatted string that displays the status and details of a todo.
     */
    public String getInfo() {
        return "[T]" + super.getInfo();
    }

    @Override
    public String toString() {
        return getInfo();
    }
}
