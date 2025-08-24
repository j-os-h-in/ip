import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class King {
    public static void main(String[] args) {
        // Initialise UI and kingStorage
        KingUI kingUI = new KingUI();
        KingStorage kingStorage = new KingStorage();
        kingUI.showIntroduction();
        ArrayList<Task> list = kingStorage.loadFile();

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
            kingUI.showLine();
            try {
                // help command - lists the possible commands you can give to the King bot
                if (helpMatcher.matches()) {
                    kingUI.showHelp();
                }

                // list command - lists all the current tasks in your task list
                else if (listMatcher.matches()) {
                    kingUI.showAllList(list);
                }

                // due command - lists all tasks on a certain due date
                else if (dueMatcher.matches()) {
                    if (dueMatcher.group(1) == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); };


                    kingUI.showDueList(list, LocalDate.parse(dueMatcher.group(1)));

                }
                else if (todoMatcher.matches()){
                    Todo newTask = new Todo(todoMatcher.group(1));
                    list.add(newTask);
                    kingStorage.addToFile(newTask);
                    kingUI.showTaskCreate(newTask, list.size());
                }
                else if (deadlineMatcher.matches()){
                    if (deadlineMatcher.group(2) == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); }

                    Deadline newTask = new Deadline(deadlineMatcher.group(1), LocalDate.parse(deadlineMatcher.group(2)));
                    list.add(newTask);
                    kingStorage.addToFile(newTask);
                    kingUI.showTaskCreate(newTask, list.size());
                }
                else if (eventMatcher.matches()){
                    if (eventMatcher.group(2) == null && eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE); }
                    else if (eventMatcher.group(2) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE); }
                    else if (eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE); }

                    Event newTask = new Event(eventMatcher.group(1), LocalDate.parse(eventMatcher.group(2)), LocalDate.parse(eventMatcher.group(3)));
                    list.add(newTask);
                    kingStorage.addToFile(newTask);
                    kingUI.showTaskCreate(newTask, list.size());
                }
                else if (markMatcher.matches()){
                    if (markMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.MARK_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(markMatcher.group(1));
                        list.get(idx - 1).markDone();
                        kingStorage.markDone(idx - 1);
                        kingUI.showMark(list.get(idx - 1));
                    }
                }
                else if (unmarkMatcher.matches()){
                    if (unmarkMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.UNMARK_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(unmarkMatcher.group(1));
                        list.get(idx - 1).unmarkDone();
                        kingStorage.unmarkDone(idx - 1);
                        kingUI.showUnmark(list.get(idx - 1));
                    }
                }
                else if (deleteMatcher.matches()){
                    if (deleteMatcher.group(1) == null) {
                        throw new KingException(KingException.ErrorMessage.DELETE_MISSING_INDEX);
                    }
                    else {
                        int idx = Integer.parseInt(deleteMatcher.group(1));
                        Task deletedTask = list.remove(idx - 1);
                        kingStorage.remove(idx - 1);
                        kingUI.showDelete(deletedTask, list.size());
                    }
                }
                else {
                    kingUI.showInvalidCommand();
                }
            }
            catch (KingException e) {
                kingUI.showError(e);
            }
            catch (IndexOutOfBoundsException e) {
                kingUI.showInvalidTask();
            }
            catch (DateTimeParseException e) {
                kingUI.showInvalidDateTime();
            }
            finally {
                kingUI.showLine();
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
        kingUI.showBye();
    }
}