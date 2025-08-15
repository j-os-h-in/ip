public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws KingException {
        if (description == null) {
            throw new KingException("Error! Missing task description! Type it after todo/deadline/event.");
        }
        else {
            this.description = description;
            this.isDone = false;
        }
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
