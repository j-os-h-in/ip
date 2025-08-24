public abstract class Task {
    private String description;
    private boolean complete;
    public enum Type {
        TODO,
        DEADLINE,
        EVENT
    }

    /**
     * Instantiates a task based on the description.
     * If no description is provided, throws a missing description exception.
     * @param description Description of the task.
     * @throws KingException Error in creation of task.
     */
    public Task(String description) throws KingException {
        if (description == null || description.isEmpty()) {
            throw new KingException(KingException.ErrorMessage.MISSING_TASK_DESCRIPTION);
        }
        else {
            this.description = description;
            this.complete = false;
        }
    }

    /**
     * Returns the type of the task.
     * @return Task type.
     */
    public abstract Type getType();

    /**
     * Returns the description of the task.
     * @return Description of task.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the completion status of the task.
     * @return If task is complete return true, else false.
     */
    public boolean getComplete() {
        return this.complete;
    }

    /**
     * Returns the completion status icon "X" of the task.
     * @return If task is complete return "X", else " ".
     */
    public String getStatusIcon() {
        return (complete ? "X" : " ");
    }

    /**
     * Sets the description of the task.
     * @param description Description of task.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the task to complete.
     */
    public void markDone() {
        this.complete = true;
    }

    /**
     * Sets the task to incomplete.
     */
    public void unmarkDone() {
        this.complete = false;
    }

    /**
     * Returns the string representation of the task.
     * @return String representation of task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
