package king.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import king.KingException;
import king.task.Deadline;
import king.task.Task;

/**
 * UI Manager for the King that helps with printing of statements
 */
public class KingUI {
    private final String spacer = "    ";

    /**
     * Show introduction message at start of conversation
     */
    public void showIntroduction() {
        showLine();
        System.out.println(spacer + "Hello! I'm King!");
        System.out.println(spacer + "What can I do for you? Use `help` to view all my commands.");
        showLine();
    }

    /**
     * Show help message for help command
     */
    public void showHelp() {
        System.out.println(spacer + " Need help? Here is the list of commands you can use to use this chat bot!");
        System.out.println(spacer + " list                                                  "
                + "- Gets the list of tasks you currently have");
        System.out.println(spacer + " due [YYYY-MM-DD]                                      "
                + "- Gets tasks due on specific date");
        System.out.println(spacer + " todo [task name]                                      "
                + "- Creates a new todo task");
        System.out.println(spacer + " deadline [task name] /by [YYYY-MM-DD]                 "
                + "- Creates a new deadline task with a time to complete by");
        System.out.println(spacer + " event [task name] /from [YYYY-MM-DD] /to [YYYY-MM-DD] "
                + "- Creates a new event based on a period");
        System.out.println(spacer + " mark [index]                                          "
                + "- Marks the task at the index to be complete");
        System.out.println(spacer + " unmark [index]                                        "
                + "- Marks the task at the index to be incomplete");
        System.out.println(spacer + " delete [index]                                        "
                + "- Deletes the task at the index");
        System.out.println(spacer + " bye                                                   "
                + "- Ends the program");
        System.out.println(spacer + " help                                                  "
                + "- Provides the list of commands to query the bot");
    }

    /**
     * Show all tasks for list command
     *
     * @param list List of all tasks
     */
    public void showAllList(ArrayList<Task> list) {
        System.out.println(spacer + " Here are the tasks in your list:");
        for (int i = 1; i <= list.size(); i++) {
            System.out.println(spacer + " " + i + ". " + list.get(i - 1));
        }
    }

    /**
     * Show specific tasks due for due command
     *
     * @param list List of all tasks
     * @param date Date of task due
     */
    public void showDueList(ArrayList<Task> list, LocalDate date) {
        System.out.println(spacer + " Here are the tasks due on:"
                + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ".");
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i - 1).getType() == Task.Type.DEADLINE) {
                Deadline deadlineTask = (Deadline) list.get(i - 1);
                if (deadlineTask.getBy().equals(date)) {
                    System.out.println(spacer + " " + i + ". " + list.get(i - 1));
                }
            }
        }
    }

    /**
     * Shows specific tasks matching find command
     *
     * @param list   List of all tasks
     * @param search Search string for tasks
     */
    public void showFindList(ArrayList<Task> list, String search) {
        System.out.println(spacer + " Here are the matching tasks in your list:");
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i - 1).getDescription().contains(search)) {
                System.out.println(spacer + " " + i + ". " + list.get(i - 1));
            }
        }
    }


    /**
     * Show specific task created for todo / deadline / event command
     *
     * @param task Task to be created
     * @param size Updated size of task list
     */
    public void showTaskCreate(Task task, int size) {
        System.out.println(spacer + " Ok! I've added this task:");
        System.out.println(spacer + "   " + task);
        System.out.println(spacer + " Now you have " + size + " tasks in the list.");
    }

    /**
     * Show specific task marked for mark command
     *
     * @param task Task marked
     */
    public void showMark(Task task) {
        System.out.println(spacer + " Nice! I've marked this task as done:");
        System.out.println(spacer + "   " + task);
    }

    /**
     * Show specific task unmarked for unmark command
     *
     * @param task Task unmarked
     */
    public void showUnmark(Task task) {
        System.out.println(spacer + " OK :( I've marked this task as not done yet");
        System.out.println(spacer + "   " + task);
    }

    /**
     * Show specific task deleted for delete command
     *
     * @param task Task deleted
     * @param size Updated size of task list
     */
    public void showDelete(Task task, int size) {
        System.out.println(spacer + " Noted! I've deleted this task:");
        System.out.println(spacer + "   " + task);
        System.out.println(spacer + " Now you have " + size + " tasks in the list.");
    }

    /**
     * Show bye message for bye command
     */
    public void showBye() {
        showLine();
        System.out.println(spacer + " BYE BYE!! Hope to see you again soon!");
        showLine();
    }

    /**
     * Show message for invalid command
     */
    public void showInvalidCommand() {
        System.out.println(spacer + " Error! Invalid command.");
    }

    /**
     * Show message for invalid task
     */
    public void showInvalidTask() {
        System.out.println(spacer + " Error! Task does not exist.");
    }

    /**
     * Show message for invalid date time
     */
    public void showInvalidDateTime() {
        System.out.println(spacer + " Error! Date time format specified is incorrect. Use format YYYY-MM-DD.");
    }

    /**
     * Show message for KingException
     *
     * @param e Exception given for error in user IO
     */
    public void showError(KingException e) {
        System.out.println(spacer + e.getMessage());
    }

    /**
     * Show line for separator
     */
    public void showLine() {
        System.out.println(spacer + "____________________________________________________________");
    }
}
