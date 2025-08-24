import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class KingStorage {
    String databasePath = "data" + FileSystems.getDefault().getSeparator() + "king.txt";
    File database = new File(databasePath);
    private enum STORAGE_ACTIONS {
        MARK_DONE,
        UNMARK_DONE,
        DELETE_TASK
    }

    public KingStorage() {
        if (!database.exists()) createFile();
    }

    public void createFile() {
        try {
            // Create parent folders
            if(database.getParentFile() != null && !database.getParentFile().exists()) {
                boolean success = database.getParentFile().mkdirs();
            }

            // Create database file
            if(!database.createNewFile()) {
                System.out.println("[KingStorage] The database file already exists or could not be created.");
            }
        }
        catch (IOException ioe) {
            System.out.println("[KingStorage] An error occurred when creating the database file: " + ioe);
            System.out.println("[KingStorage] Your data is not saved.");
        }
    }

    public void addToFile(Task task) {
        if (!database.exists()) createFile();
        try {
            FileWriter fw = new FileWriter(databasePath, true);
            switch(task.getType()) {
            case "TODO":
                Todo todo = (Todo) task;
                fw.write("T | " + (todo.isDone ? 1 : 0) + " | " + todo.getDescription() + "\n");
                break;
            case "DEADLINE":
                Deadline deadline = (Deadline) task;
                fw.write("D | " + (deadline.isDone ? 1 : 0) + " | " + deadline.getDescription() + " | " + deadline.getBy() + "\n");
                break;
            case "EVENT":
                Event event = (Event) task;
                fw.write("E | " + (event.isDone ? 1 : 0) + " | " + event.getDescription() + " | " + event.getFrom() + " | " + event.getTo() + "\n");
                break;
            }
            fw.close();
        }
        catch(IOException ioe) {
            System.out.println("[KingStorage] Exception when adding task to file: " + ioe);
        }
    }

    public void markDone(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.MARK_DONE);
    }

    public void unmarkDone(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.UNMARK_DONE);
    }

    public void remove(int index) {
        if (!database.exists()) createFile();
        replaceLine(index, STORAGE_ACTIONS.DELETE_TASK);
    }

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
                        Deadline newDeadline = new Deadline(taskStrings[2], taskStrings[3]);
                        if (taskStrings[1].equals("1")) newDeadline.markDone();
                        tasks.add(newDeadline);
                        break;
                    case "E":
                        Event newEvent = new Event(taskStrings[2], taskStrings[3], taskStrings[4]);
                        if (taskStrings[1].equals("1")) newEvent.markDone();
                        tasks.add(newEvent);
                        break;
                    default:
                        throw new KingException("[KingStorage] Invalid data in database.");
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
                System.out.println("[KingStorage] Database has been reset.");
            }
            else {
                System.out.println("[KingStorage] Operation cancelled. Your tasks created will not be saved.");
            }

        }
        catch (ArrayIndexOutOfBoundsException aie) {
            System.out.println("[KingStorage] File may be corrupted: " + aie);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("[KingStorage] File not found: " + fnfe);
        }
        return null;
    }

    // String Helpers
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
                    break;
                case UNMARK_DONE:
                    taskStrings[1] = "0";
                    break;
                case DELETE_TASK:
                    allLines.remove(row);
                default:
                    break;
                }
                allLines.set(row, String.join(" | ", taskStrings));
            }
            Files.write(path, allLines);
        }
        catch (IOException ioe) {
            System.out.println("[KingDatabase] Unable to edit database, changes were not saved.");
        }
    }
}
