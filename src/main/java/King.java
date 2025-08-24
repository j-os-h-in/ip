import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class King {
    public static void main(String[] args) {
        // Initialise bot
        String spacer = "    ";
        System.out.println(spacer + "____________________________________________________________");
        System.out.println(spacer + "Hello! I'm King!");
        System.out.println(spacer + "What can I do for you?");
        System.out.println(spacer + "____________________________________________________________");

        // Initialise database
        KingStorage database = new KingStorage();
        ArrayList<Task> list = database.loadFile();
        if (list == null) list = new ArrayList<>();

        // Initialise input scanner
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        // Initialise regexes and matchers
        String helpRegex        = "^help$";
        String listRegex        = "^list$";
        String dueRegex         = "^due(?:\\s+(.*))?$";
        String todoRegex        = "^todo(?:\\s+(.*))?$";
        String deadlineRegex    = "^deadline(?:\\s+(.*?)\\s*(?:/by\\s+(.+))?)?$";
        String eventRegex       = "^event(?:\\s+(.*?)(?:\\s+/from\\s+(.+?))?(?:\\s+/to\\s+(.+))?)?$";
        String markRegex        = "^mark(?:\\s+(\\d*))?$";
        String unmarkRegex      = "^unmark(?:\\s+(\\d*))?$";
        String deleteRegex      = "^delete(?:\\s+(\\d*))?$";
        String endRegex         = "^bye$";

        Matcher helpMatcher     = Pattern.compile(helpRegex).matcher(text);
        Matcher listMatcher     = Pattern.compile(listRegex).matcher(text);
        Matcher dueMatcher      = Pattern.compile(dueRegex).matcher(text);
        Matcher todoMatcher     = Pattern.compile(todoRegex).matcher(text);
        Matcher deadlineMatcher = Pattern.compile(deadlineRegex).matcher(text);
        Matcher eventMatcher    = Pattern.compile(eventRegex).matcher(text);
        Matcher markMatcher     = Pattern.compile(markRegex).matcher(text);
        Matcher unmarkMatcher   = Pattern.compile(unmarkRegex).matcher(text);
        Matcher deleteMatcher   = Pattern.compile(deleteRegex).matcher(text);
        Matcher endMatcher      = Pattern.compile(endRegex).matcher(text);

        // Loop to get user input
        while (!endMatcher.matches()) {
            System.out.println(spacer + "____________________________________________________________");
            try {
                // /help command - lists the possible commands you can give to the King bot
                if (helpMatcher.matches()) {
                    System.out.println(spacer + " Need help? Here is the list of commands you can use to use this chat bot!");
                    System.out.println(spacer + " /list                                                  - Gets the list of tasks you currently have");
                    System.out.println(spacer + " /due [YYYY-MM-DD]                                      - Gets tasks due on specific date");
                    System.out.println(spacer + " /todo [task name]                                      - Creates a new todo task");
                    System.out.println(spacer + " /deadline [task name] /by [YYYY-MM-DD]                 - Creates a new deadline task with a time to complete by");
                    System.out.println(spacer + " /event [task name] /from [YYYY-MM-DD] /to [YYYY-MM-DD] - Creates a new event based on a period");
                    System.out.println(spacer + " /mark [index]                                          - Marks the task at the index to be complete");
                    System.out.println(spacer + " /unmark [index]                                        - Marks the task at the index to be incomplete");
                    System.out.println(spacer + " /delete [index]                                        - Deletes the task at the index");
                    System.out.println(spacer + " /bye                                                   - Ends the program");
                    System.out.println(spacer + " /help                                                  - Provides the list of commands to query the bot");
                }

                // /list command - lists all the current tasks in your task list
                else if (listMatcher.matches()) {
                    System.out.println(spacer + " Here are the tasks in your list:");
                    for (int i = 1; i <= list.size(); i++) {
                        System.out.println(spacer + " " + i + ". " + list.get(i - 1));
                    }
                }

                // /due command - lists all tasks on a certain due date
                else if (dueMatcher.matches()) {
                    if (dueMatcher.group(1) == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); }

                    System.out.println(spacer + " Here are the tasks due on " + LocalDate.parse(dueMatcher.group(1)).format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ".");
                    for (int i = 1; i <= list.size(); i++) {
                        if (list.get(i - 1).getType() == Task.Type.DEADLINE) {
                            Deadline deadlineTask = (Deadline) list.get(i - 1);
                            if (deadlineTask.getBy().equals(LocalDate.parse(dueMatcher.group(1)))) {
                                System.out.println(spacer + " " + i + ". " + list.get(i - 1));
                            }
                        }
                    }
                }
                else if (todoMatcher.matches()){
                    Todo newTask = new Todo(todoMatcher.group(1));
                    list.add(newTask);
                    database.addToFile(newTask);
                    System.out.println(spacer + " Ok! I've added this todo:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else if (deadlineMatcher.matches()){
                    if (deadlineMatcher.group(2) == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); }

                    Deadline newTask = new Deadline(deadlineMatcher.group(1), LocalDate.parse(deadlineMatcher.group(2)));
                    list.add(newTask);
                    database.addToFile(newTask);
                    System.out.println(spacer + " Ok! I've added this deadline:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else if (eventMatcher.matches()){
                    if (eventMatcher.group(2) == null && eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE); }
                    else if (eventMatcher.group(2) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE); }
                    else if (eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE); }

                    Event newTask = new Event(eventMatcher.group(1), LocalDate.parse(eventMatcher.group(2)), LocalDate.parse(eventMatcher.group(3)));
                    list.add(newTask);
                    database.addToFile(newTask);
                    System.out.println(spacer + " Ok! I've added this event:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else if (markMatcher.matches()){
                    if (markMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.MARK_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(markMatcher.group(1));
                        list.get(idx - 1).markDone();
                        database.markDone(idx - 1);
                        System.out.println(spacer + " NICE!! I've marked this task as done:");
                        System.out.println(spacer + "   " + list.get(idx - 1));
                    }
                }
                else if (unmarkMatcher.matches()){
                    if (unmarkMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.UNMARK_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(unmarkMatcher.group(1));
                        list.get(idx - 1).unmarkDone();
                        database.unmarkDone(idx - 1);
                        System.out.println(spacer + " OK :( I've marked this task as not done yet");
                        System.out.println(spacer + "   " + list.get(idx - 1));
                    }
                }
                else if (deleteMatcher.matches()){
                    if (deleteMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.DELETE_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(deleteMatcher.group(1));
                        Task deletedTask = list.remove(idx - 1);
                        database.remove(idx - 1);
                        System.out.println(spacer + " Noted! I've deleted this task:");
                        System.out.println(spacer + "   " + deletedTask);
                        System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                    }
                }
                else {
                    System.out.println(spacer + " Error! Invalid command.");
                }
            }
            catch (KingException e) {
                System.out.println(spacer + e.getMessage());
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(spacer + " Error! Task does not exist... ");
            }
            catch (DateTimeParseException e) {
                System.out.println(spacer + " Error! Date time format specified is incorrect. Use format YYYY-MM-DD");
            }
            finally {
                System.out.println(spacer + "____________________________________________________________");
                text = scanner.nextLine().strip();
                helpMatcher     = Pattern.compile(helpRegex).matcher(text);
                listMatcher     = Pattern.compile(listRegex).matcher(text);
                dueMatcher      = Pattern.compile(dueRegex).matcher(text);
                todoMatcher     = Pattern.compile(todoRegex).matcher(text);
                deadlineMatcher = Pattern.compile(deadlineRegex).matcher(text);
                eventMatcher    = Pattern.compile(eventRegex).matcher(text);
                markMatcher     = Pattern.compile(markRegex).matcher(text);
                unmarkMatcher   = Pattern.compile(unmarkRegex).matcher(text);
                deleteMatcher   = Pattern.compile(deleteRegex).matcher(text);
                endMatcher      = Pattern.compile(endRegex).matcher(text);
            }
        }
        scanner.close();
        System.out.println(spacer + "____________________________________________________________");
        System.out.println(spacer + " BYE BYE!! Hope to see you again soon!");
        System.out.println(spacer + "____________________________________________________________");
    }
}