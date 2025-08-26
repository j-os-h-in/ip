package king.task;

import king.storage.KingStorage;

import java.util.ArrayList;

/**
 * Tasklist for the King to help with task management and storage
 */
public class KingTaskList {
    private ArrayList<Task> tasks;
    private KingStorage kingStorage = new KingStorage();

    /**
     * Instantiates a task list with data loaded from the database
     */
    public KingTaskList() {
        ArrayList<Task> tasks = kingStorage.loadFile();
        if (tasks == null) this.tasks = new ArrayList<>();
        else this.tasks = tasks;
    }

    /**
     * Instantiates an empty task list with the data from the database reset
     * @param reset If reset is true, reset the database.
     */
    public KingTaskList(boolean reset) {
        this();
        if (reset) resetList();
    }

    /**
     * Gets the task list
     * @return Tasklist of user
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a new task to the task list and database
     * @param newTask New task to be added into the task list and database
     */
    public void addTask(Task newTask) {
        tasks.add(newTask);
        kingStorage.addToFile(newTask);
    }

    /**
     * Gets the task at specific index of the task list
     * @param idx Index to get task from
     * @return Task at index
     */
    public Task getTask(int idx) {
        return tasks.get(idx);
    }

    /**
     * Marks the task at specific index of the task list done
     * @param idx Index to get task
     */
    public void markDone(int idx) {
        tasks.get(idx).markDone();
        kingStorage.markDone(idx);
    }

    /**
     * Unmarks the task at specific index of the task list done
     * @param idx Index to get task
     */
    public void unmarkDone(int idx) {
        tasks.get(idx).unmarkDone();
        kingStorage.unmarkDone(idx);
    }

    /**
     * Deletes the task at specific index of the task list
     * @param idx Index to get task
     */
    public Task deleteTask(int idx) {
        Task deletedTask = tasks.remove(idx);
        kingStorage.remove(idx);
        return deletedTask;
    }

    /**
     * Resets the task list in the database and array
     */
    private void resetList() {
        tasks.clear();
        kingStorage.resetFile();
    }

    /**
     * Gets the size of the task list
     * @return Size of task list
     */
    public int getSize() {
        return tasks.size();
    }
}
