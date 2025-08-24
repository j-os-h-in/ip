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
        KingParser kingParser = new KingParser("");

        kingUI.showIntroduction();
        ArrayList<Task> list = kingStorage.loadFile();
        if (list == null) list = new ArrayList<>();

        // Initialise input scanner
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        // Loop to get user input
        try {
            while (!kingParser.checkParser(KingParser.Commands.BYE)) {
                kingUI.showLine();
                try {
                    // help command - lists the possible commands you can give to the King bot
                    if (kingParser.checkParser(KingParser.Commands.HELP)) {
                        kingUI.showHelp();
                    }

                    // list command - lists all the current tasks in your task list
                    else if (kingParser.checkParser(KingParser.Commands.LIST)) {
                        kingUI.showAllList(list);
                    }

                    // due command - lists all tasks on a certain due date
                    else if (kingParser.checkParser(KingParser.Commands.DUE)) {
                        kingUI.showDueList(list, LocalDate.parse(kingParser.getDueMatcher().group(1)));
                    }

                    // todo command - creates a new todo task
                    else if (kingParser.checkParser(KingParser.Commands.TODO)){
                        Todo newTask = new Todo(kingParser.getTodoMatcher().group(1));
                        list.add(newTask);
                        kingStorage.addToFile(newTask);
                        kingUI.showTaskCreate(newTask, list.size());
                    }

                    // deadline command - creates a new deadline task
                    else if (kingParser.checkParser(KingParser.Commands.DEADLINE)){
                        Deadline newTask = new Deadline(kingParser.getDeadlineMatcher().group(1), LocalDate.parse(kingParser.getDeadlineMatcher().group(2)));
                        list.add(newTask);
                        kingStorage.addToFile(newTask);
                        kingUI.showTaskCreate(newTask, list.size());
                    }

                    // event command - creates a new event task
                    else if (kingParser.checkParser(KingParser.Commands.EVENT)){
                        Event newTask = new Event(kingParser.getEventMatcher().group(1), LocalDate.parse(kingParser.getEventMatcher().group(2)), LocalDate.parse(kingParser.getEventMatcher().group(3)));
                        list.add(newTask);
                        kingStorage.addToFile(newTask);
                        kingUI.showTaskCreate(newTask, list.size());
                    }

                    // mark command - marks a task complete
                    else if (kingParser.checkParser(KingParser.Commands.MARK)){
                        int idx = Integer.parseInt(kingParser.getMarkMatcher().group(1));
                        list.get(idx - 1).markDone();
                        kingStorage.markDone(idx - 1);
                        kingUI.showMark(list.get(idx - 1));
                    }

                    // unmark command - marks a task incomplete
                    else if (kingParser.checkParser(KingParser.Commands.UNMARK)){
                        int idx = Integer.parseInt(kingParser.getUnmarkMatcher().group(1));
                        list.get(idx - 1).unmarkDone();
                        kingStorage.unmarkDone(idx - 1);
                        kingUI.showUnmark(list.get(idx - 1));
                    }

                    // delete command - deletes a task
                    else if (kingParser.checkParser(KingParser.Commands.DELETE)){
                        int idx = Integer.parseInt(kingParser.getDeleteMatcher().group(1));
                        Task deletedTask = list.remove(idx - 1);
                        kingStorage.remove(idx - 1);
                        kingUI.showDelete(deletedTask, list.size());
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
                    kingParser.setNewInput(text);
                }
            }
            scanner.close();
            kingUI.showBye();
        }

        catch (KingException e) {
            kingUI.showError(e);
        }
    }
}