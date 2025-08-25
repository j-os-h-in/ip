package king.storage;

import king.KingException;
import king.task.Deadline;
import king.task.Event;
import king.task.Task;
import king.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class KingStorage {
    private final String databasePath = "data" + FileSystems.getDefault().getSeparator() + "king.txt";
    private final File database = new File(databasePath);
    private enum STORAGE_ACTIONS {
        MARK_DONE,
        UNMARK_DONE,
        DELETE_TASK
    }

    /**
     * Instantiates the database. If the database file has not been created, it creates the file.
     */
    public KingStorage() {
        if (!database.exists()) createFile();
    }

    /**
     * Creates the database file.
     */
    public void createFile() {
        try {
            // Create parent folders
            if(database.getParentFile() != null && !database.getParentFile().exists()) {
                boolean success = database.getParentFile().mkdirs();
            }

            // Create database file
            if(!database.createNewFile()) {
                System.out.println("[king.storage.KingStorage] The database file already exists or could not be created.");
            }
        }
        catch (IOException ioe) {
            System.out.println("[king.storage.KingStorage] An error occurred when creating the database file: " + ioe);
            System.out.println("[king.storage.KingStorage] Your data is not saved.");
        }
    }

    /**
     * Adds a task to the database file.
     * @param task king.task.Task to be added to the database.
     */
    public void addToFile(Task task) {
        if (!database.exists()) createFile();
        try {
            FileWriter fw = new FileWriter(databasePath, true);
            switch(task.getType()) {
            case Task.Type.TODO:
                Todo todo = (Todo) task;
                fw.write("T | " + (todo.getComplete() ? 1 : 0) + " | " + todo.getDescription() + "\n");
                break;
            case Task.Type.DEADLINE:
                Deadline deadline = (Deadline) task;
                fw.write("D | " + (deadline.getComplete() ? 1 : 0) + " | " + deadline.getDescription() + " | " + deadline.getBy() + "\n");
                break;
            case Task.Type.EVENT:
                Event event = (Event) task;
                fw.write("E | " + (event.getComplete() ? 1 : 0) + " | " + event.getDescription() + " | " + event.getFrom() + " | " + event.getTo() + "\n");
                break;
            }
            fw.close();
        }
        catch(IOException ioe) {
            System.out.println("[king.storage.KingStorage] Exception when adding task to file: " + ioe);
        }
    }

    /**
     * Marks a task in the database file as complete.
     * @param index Index of task to be marked complete.
     */
    public void markDone(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.MARK_DONE);
    }

    /**
     * Marks a task in the database file as incomplete.
     * @param index Index of task to be marked incomplete.
     */
    public void unmarkDone(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.UNMARK_DONE);
    }

    /**
     * Removes a task in the database file.
     * @param index Index of task to be removed.
     */
    public void remove(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.DELETE_TASK);
    }

    /**
     * Loads the tasks in the database file into an ArrayList of Tasks.
     * @return ArraysList of Tasks.
     */
    public ArrayList<Task> loadFile() {
        if (!database.exists()) return null;
        try {
            Scanner s = new Scanner(database);
            ArrayList<Task> tasks = new ArrayList<>();
            while (s.hasNext()) {
                String[] taskStrings = s.nextLine().split("\\s*\\|\\s*");

                switch (taskStrings[0]) {
                    case "T":
                        Todo newTodo = new Todo(taskStrings[2]);
                        if (taskStrings[1].equals("1")) newTodo.markDone();
                        tasks.add(newTodo);
                        break;
                    case "D":
                        Deadline newDeadline = new Deadline(taskStrings[2], LocalDate.parse(taskStrings[3]));
                        if (taskStrings[1].equals("1")) newDeadline.markDone();
                        tasks.add(newDeadline);
                        break;
                    case "E":
                        Event newEvent = new Event(taskStrings[2], LocalDate.parse(taskStrings[3]), LocalDate.parse(taskStrings[4]));
                        if (taskStrings[1].equals("1")) newEvent.markDone();
                        tasks.add(newEvent);
                        break;
                    default:
                        throw new KingException(KingException.ErrorMessage.INVALID_DATABASE);
                }
            }
            s.close();
            return tasks;
        }
        catch (KingException ke) {
            System.out.println(ke.getMessage() + " Would you like to reset the database? [Y / N]");
            Scanner scanner = new Scanner (System.in);
            String text = scanner.nextLine();
            while (!text.equals("Y") && !text.equals("N")) {
                System.out.println(ke.getMessage() + " Would you like to reset the database? [Y / N]");
                text = scanner.nextLine();
            }
            if (text.equals("Y")) {
                database.delete();
                createFile();
                System.out.println("[king.storage.KingStorage] Database has been reset.");
            }
            else {
                System.out.println("[king.storage.KingStorage] Operation cancelled. Your tasks created will not be saved.");
            }

        }
        catch (ArrayIndexOutOfBoundsException aie) {
            System.out.println("[king.storage.KingStorage] File may be corrupted: " + aie);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("[king.storage.KingStorage] File not found: " + fnfe);
        }
        return null;
    }

    /**
     * Helper function to replace line in the database file.
     * @param row Row number of line to be edited / replaced.
     * @param sa Action to be done on the line in the database file.
     */
    private void replaceLine(int row, STORAGE_ACTIONS sa) {
        try {
            Path path = Paths.get(databasePath);
            ArrayList<String> allLines = new ArrayList<>(Files.readAllLines(path));
            if (row >= 0 && row < allLines.size()) {
                String line = allLines.get(row);
                String[] taskStrings = line.split("\\s*\\|\\s*");
                switch(sa) {
                case MARK_DONE:
                    taskStrings[1] = "1";
                    allLines.set(row, String.join(" | ", taskStrings));
                    break;
                case UNMARK_DONE:
                    taskStrings[1] = "0";
                    allLines.set(row, String.join(" | ", taskStrings));
                    break;
                case DELETE_TASK:
                    allLines.remove(row);
                default:
                    break;
                }
            }
            Files.write(path, allLines);
        }
        catch (IOException ioe) {
            System.out.println("[KingDatabase] Unable to edit database, changes were not saved.");
        }
    }
}
