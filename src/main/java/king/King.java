package king;

import king.parser.KingParser;
import king.task.Deadline;
import king.task.Event;
import king.task.KingTaskList;
import king.task.Todo;
import king.ui.KingUI;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class King {
    public static void main(String[] args) {
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
                    // help command - lists the possible commands you can give to the king.King bot
                    if (kingParser.checkParser(KingParser.Commands.HELP)) {
                        kingUI.showHelp();
                    }

                    // list command - lists all the current tasks in your task list
                    else if (kingParser.checkParser(KingParser.Commands.LIST)) {
                        kingUI.showAllList(kingTaskList.getTasks());
                    }

                    // due command - lists all tasks on a certain due date
                    else if (kingParser.checkParser(KingParser.Commands.DUE)) {
                        kingUI.showDueList(kingTaskList.getTasks(), LocalDate.parse(kingParser.getDueMatcher().group(1)));
                    }

                    // todo command - creates a new todo task
                    else if (kingParser.checkParser(KingParser.Commands.TODO)) {
                        Todo newTask = new Todo(kingParser.getTodoMatcher().group(1));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    }

                    // deadline command - creates a new deadline task
                    else if (kingParser.checkParser(KingParser.Commands.DEADLINE)) {
                        Deadline newTask = new Deadline(kingParser.getDeadlineMatcher().group(1), LocalDate.parse(kingParser.getDeadlineMatcher().group(2)));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    }

                    // event command - creates a new event task
                    else if (kingParser.checkParser(KingParser.Commands.EVENT)) {
                        Event newTask = new Event(kingParser.getEventMatcher().group(1), LocalDate.parse(kingParser.getEventMatcher().group(2)), LocalDate.parse(kingParser.getEventMatcher().group(3)));
                        kingTaskList.addTask(newTask);
                        kingUI.showTaskCreate(newTask, kingTaskList.getSize());
                    }

                    // mark command - marks a task complete
                    else if (kingParser.checkParser(KingParser.Commands.MARK)) {
                        int idx = Integer.parseInt(kingParser.getMarkMatcher().group(1)) - 1;
                        kingTaskList.markDone(idx);
                        kingUI.showMark(kingTaskList.getTask(idx));
                    }

                    // unmark command - marks a task incomplete
                    else if (kingParser.checkParser(KingParser.Commands.UNMARK)) {
                        int idx = Integer.parseInt(kingParser.getUnmarkMatcher().group(1)) - 1;
                        kingTaskList.unmarkDone(idx);
                        kingUI.showUnmark(kingTaskList.getTask(idx));
                    }

                    // delete command - deletes a task
                    else if (kingParser.checkParser(KingParser.Commands.DELETE)) {
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