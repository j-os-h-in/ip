package king.ui;

import king.task.Deadline;
import king.KingException;
import king.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class KingUI {
    private final String spacer = "    ";

    public void showIntroduction() {
        showLine();
        System.out.println(spacer + "Hello! I'm King!");
        System.out.println(spacer + "What can I do for you? Use `help` to view all my commands.");
        showLine();
    }

    public void showHelp() {
        System.out.println(spacer + " Need help? Here is the list of commands you can use to use this chat bot!");
        System.out.println(spacer + " list                                                  - Gets the list of tasks you currently have");
        System.out.println(spacer + " due [YYYY-MM-DD]                                      - Gets tasks due on specific date");
        System.out.println(spacer + " todo [task name]                                      - Creates a new todo task");
        System.out.println(spacer + " deadline [task name] /by [YYYY-MM-DD]                 - Creates a new deadline task with a time to complete by");
        System.out.println(spacer + " event [task name] /from [YYYY-MM-DD] /to [YYYY-MM-DD] - Creates a new event based on a period");
        System.out.println(spacer + " mark [index]                                          - Marks the task at the index to be complete");
        System.out.println(spacer + " unmark [index]                                        - Marks the task at the index to be incomplete");
        System.out.println(spacer + " delete [index]                                        - Deletes the task at the index");
        System.out.println(spacer + " bye                                                   - Ends the program");
        System.out.println(spacer + " help                                                  - Provides the list of commands to query the bot");
    }

    public void showAllList(ArrayList<Task> list) {
        System.out.println(spacer + " Here are the tasks in your list:");
        for (int i = 1; i <= list.size(); i++) {
            System.out.println(spacer + " " + i + ". " + list.get(i - 1));
        }
    }

    public void showDueList(ArrayList<Task> list, LocalDate date) {
        System.out.println(spacer + " Here are the tasks due on:" + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ".");
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i - 1).getType() == Task.Type.DEADLINE) {
                Deadline deadlineTask = (Deadline) list.get(i - 1);
                if (deadlineTask.getBy().equals(date)) {
                    System.out.println(spacer + " " + i + ". " + list.get(i - 1));
                }
            }
        }
    }

    public void showTaskCreate(Task task, int size) {
        System.out.println(spacer + " Ok! I've added this task:");
        System.out.println(spacer + "   " + task);
        System.out.println(spacer + " Now you have " + size + " tasks in the list.");
    }

    public void showMark(Task task) {
        System.out.println(spacer + " Nice! I've marked this task as done:");
        System.out.println(spacer + "   " + task);
    }

    public void showUnmark(Task task) {
        System.out.println(spacer + " OK :( I've marked this task as not done yet");
        System.out.println(spacer + "   " + task);
    }

    public void showDelete(Task task, int size) {
        System.out.println(spacer + " Noted! I've deleted this task:");
        System.out.println(spacer + "   " + task);
        System.out.println(spacer + " Now you have " + size + " tasks in the list.");
    }

    public void showBye() {
        showLine();
        System.out.println(spacer + " BYE BYE!! Hope to see you again soon!");
        showLine();
    }

    public void showInvalidCommand() {
        System.out.println(spacer + " Error! Invalid command.");
    }

    public void showInvalidTask() {
        System.out.println(spacer + " Error! Task does not exist.");
    }

    public void showInvalidDateTime() {
        System.out.println(spacer + " Error! Date time format specified is incorrect. Use format YYYY-MM-DD.");
    }

    public void showError(KingException e) {
        System.out.println(spacer + e.getMessage());
    }

    public void showLine() {
        System.out.println(spacer + "____________________________________________________________");
    }
}
