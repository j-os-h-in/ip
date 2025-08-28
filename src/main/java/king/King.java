package king;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import javafx.application.Application;
import king.parser.KingParser;
import king.task.Deadline;
import king.task.Event;
import king.task.KingTaskList;
import king.task.Todo;
import king.ui.KingGui;
import king.ui.KingUI;

/**
 * King bot class that manages the parser, task list and UI
 */
public class King {
    public static void main(String[] args) {
        Application.launch(KingGui.class, args);

        // Initialise UI, Storage, Parser and TaskList
        KingUI kingUI = new KingUI();
        KingParser kingParser = new KingParser("");
        KingTaskList kingTaskList = new KingTaskList();

        // Show introduction
        kingUI.showIntroduction();

        // Initialise input scanner
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().strip();
        kingParser.setNewInput(text);

        // Loop to get user input
        try {
            while (!kingParser.checkParser(KingParser.Commands.BYE)) {
                kingUI.showLine();
                try {
                    if (kingParser.checkParser(KingParser.Commands.HELP)) {
                        // help command - lists the possible commands you can give to the king.King bot
                        kingUI.showHelp();
                    } else if (kingParser.checkParser(KingParser.Commands.LIST)) {
                        // list command - lists all the current tasks in your task list
                        kingUI.showAllList(kingTaskList.getTasks());
                    } else if (kingParser.checkParser(KingParser.Commands.DUE)) {
                        // due command - lists all tasks on a certain due date
                        kingUI.showDueList(
                                kingTaskList.getTasks(),
                                LocalDate.parse(kingParser.getDueMatcher().group(1)));
                    } else if (kingParser.checkParser(KingParser.Commands.FIND)) {
                        // find command - finds all tasks with a certain name
                        kingUI.showFindList(kingTaskList.getTasks(), kingParser.getFindMatcher().group(1));
                    } else if (kingParser.checkParser(KingParser.Commands.TODO)) {
                        // todo command - creates a new todo task
                        Todo newTask = new Todo(kingParser.getTodoMatcher().group(1));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    } else if (kingParser.checkParser(KingParser.Commands.DEADLINE)) {
                        // deadline command - creates a new deadline task
                        Deadline newTask = new Deadline(
                                kingParser.getDeadlineMatcher().group(1),
                                LocalDate.parse(kingParser.getDeadlineMatcher().group(2)));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    } else if (kingParser.checkParser(KingParser.Commands.EVENT)) {
                        // event command - creates a new event task
                        Event newTask = new Event(
                                kingParser.getEventMatcher().group(1),
                                LocalDate.parse(kingParser.getEventMatcher().group(2)),
                                LocalDate.parse(kingParser.getEventMatcher().group(3)));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    } else if (kingParser.checkParser(KingParser.Commands.MARK)) {
                        // mark command - marks a task complete
                        int idx = Integer.parseInt(kingParser.getMarkMatcher().group(1)) - 1;
                        kingTaskList.markDone(idx);
                        kingUI.showMark(kingTaskList.getTask(idx));
                    } else if (kingParser.checkParser(KingParser.Commands.UNMARK)) {
                        // unmark command - marks a task incomplete
                        int idx = Integer.parseInt(kingParser.getUnmarkMatcher().group(1)) - 1;
                        kingTaskList.unmarkDone(idx);
                        kingUI.showUnmark(kingTaskList.getTask(idx));
                    } else if (kingParser.checkParser(KingParser.Commands.DELETE)) {
                        // delete command - deletes a task
                        int idx = Integer.parseInt(kingParser.getDeleteMatcher().group(1)) - 1;
                        kingUI.showDelete(kingTaskList.deleteTask(idx), kingTaskList.getSize());
                    } else {
                        kingUI.showInvalidCommand();
                    }
                } catch (KingException e) {
                    kingUI.showError(e);
                } catch (IndexOutOfBoundsException e) {
                    kingUI.showInvalidTask();
                } catch (DateTimeParseException e) {
                    kingUI.showInvalidDateTime();
                } finally {
                    kingUI.showLine();
                    text = scanner.nextLine().strip();
                    kingParser.setNewInput(text);
                }
            }
            scanner.close();
            kingUI.showBye();
        } catch (KingException e) {
            kingUI.showError(e);
        }
    }
}
