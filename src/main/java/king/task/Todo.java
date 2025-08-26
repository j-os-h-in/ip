package king.task;

import king.KingException;

public class Todo extends Task {
    /**
     * Instantiates a todo task based on the description of the task.
     * If no description is provided, throws a missing description exception.
     *
     * @param description Description of the task.
     * @throws KingException Error in creation of task.
     */
    public Todo(String description) throws KingException {
        super(description);
    }

    @Override
    public Type getType() {
        return Type.TODO;
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
