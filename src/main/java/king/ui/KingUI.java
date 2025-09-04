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
    public String showIntroduction() {
        return ("Hello! I'm King!\nWhat can I do for you? Use `help` to view all my commands.");
    }

    /**
     * Show help message for help command
     */
    public String showHelp() {
        return " Need help? Here is the list of commands you can use to use this chat bot!\n"
                + " list - Gets the list of tasks you currently have\n"
                + " due [YYYY-MM-DD] - Gets tasks due on specific date\n"
                + " find [string1] [string2] ... - Finds tasks matching strings\n"
                + " todo [task name] - Creates a new todo task\n"
                + " deadline [task name] /by [YYYY-MM-DD] - Creates a new deadline task with a time to complete by\n"
                + " event [task name] /from [YYYY-MM-DD] /to [YYYY-MM-DD] - Creates a new event based on a period\n"
                + " mark [index] - Marks the task at the index to be complete\n"
                + " unmark [index] - Marks the task at the index to be incomplete\n"
                + " delete [index] - Deletes the task at the index\n"
                + " bye - Ends the program\n"
                + " help - Provides the list of commands to query the bot";
    }

    /**
     * Show all tasks for list command
     *
     * @param list List of all tasks
     */
    public String showAllList(ArrayList<Task> list) {
        String response = " Here are the tasks in your list:\n";
        for (int i = 1; i <= list.size(); i++) {
            response = response.concat(i + ". " + list.get(i - 1) + "\n");
        }
        return response;
    }

    /**
     * Show specific tasks due for due command
     *
     * @param list List of all tasks
     * @param date Date of task due
     */
    public String showDueList(ArrayList<Task> list, LocalDate date) {
        String response = " Here are the tasks due on:"
                + date.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ".\n";
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i - 1).getType() == Task.Type.DEADLINE) {
                Deadline deadlineTask = (Deadline) list.get(i - 1);
                if (deadlineTask.getBy().equals(date)) {
                    response = response.concat(i + ". " + list.get(i - 1) + "\n");
                }
            }
        }
        return response;
    }

    /**
     * Shows specific tasks matching find command
     *
     * @param list     List of all tasks
     * @param searches Search strings for tasks
     */
    public String showFindList(ArrayList<Task> list, String... searches) {
        String response = "Here are the matching tasks in your list:\n";
        for (int i = 1; i <= list.size(); i++) {
            for (String search : searches) {
                if (list.get(i - 1).getDescription().contains(search)) {
                    response = response.concat(i + ". " + list.get(i - 1) + "\n");
                }
            }
        }
        return response;
    }


    /**
     * Show specific task created for todo / deadline / event command
     *
     * @param task Task to be created
     * @param size Updated size of task list
     */
    public String showTaskCreate(Task task, int size) {
        String response = "Ok! I've added this task:\n";
        response = response.concat(task.toString() + "\n");
        response = response.concat("Now you have " + size + " tasks in the list.");
        return response;
    }

    /**
     * Show specific task marked for mark command
     *
     * @param task Task marked
     */
    public String showMark(Task task) {
        String response = "Nice! I've marked this task as done:\n";
        response = response.concat(task.toString());
        return response;
    }

    /**
     * Show specific task unmarked for unmark command
     *
     * @param task Task unmarked
     */
    public String showUnmark(Task task) {
        String response = "OK :( I've marked this task as not done yet\n";
        response = response.concat(task.toString());
        return response;
    }

    /**
     * Show specific task deleted for delete command
     *
     * @param task Task deleted
     * @param size Updated size of task list
     */
    public String showDelete(Task task, int size) {
        String response = "Noted! I've deleted this task:\n";
        response = response.concat(task.toString()) + "\n";
        response = response.concat("Now you have " + size + " tasks in the list.");
        return response;
    }

    /**
     * Show bye message for bye command
     */
    public String showBye() {
        return "BYE BYE!! Hope to see you again soon!";
    }

    /**
     * Show message for invalid command
     */
    public String showInvalidCommand() {
        return "Error! Invalid command.";
    }

    /**
     * Show message for invalid task
     *
     * @return String error message
     */
    public String showInvalidTask() {
        return "Error! Task does not exist.";
    }

    /**
     * Show message for invalid date time
     *
     * @return String error message
     */
    public String showInvalidDateTime() {
        return "Error! Date time format specified is incorrect. Use format YYYY-MM-DD.";
    }

    /**
     * Show message for KingException
     *
     * @param e Exception given for error in user IO
     * @return String error message
     */
    public String showError(KingException e) {
        return e.getMessage();
    }
}
