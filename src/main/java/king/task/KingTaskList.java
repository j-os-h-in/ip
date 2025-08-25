package king.task;

import king.storage.KingStorage;

import java.util.ArrayList;

public class KingTaskList {
    private ArrayList<Task> tasks;
    private KingStorage kingStorage = new KingStorage();

    public KingTaskList() {
        ArrayList<Task> tasks = kingStorage.loadFile();
        if (tasks == null) this.tasks = new ArrayList<>();
        else this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        kingStorage.addToFile(newTask);
    }

    public Task getTask(int idx) {
        return tasks.get(idx);
    }

    public void markDone(int idx) {
        tasks.get(idx).markDone();
        kingStorage.markDone(idx);
    }

    public void unmarkDone(int idx) {
        tasks.get(idx).unmarkDone();
        kingStorage.unmarkDone(idx);
    }

    public Task deleteTask(int idx) {
        Task deletedTask = tasks.remove(idx);
        kingStorage.remove(idx);
        return deletedTask;
    }

    public int getSize() {
        return tasks.size();
    }
}
