package king;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import king.parser.KingParser;
import king.task.Deadline;
import king.task.Event;
import king.task.KingTaskList;
import king.task.Todo;
import king.ui.KingUI;

/**
 * King bot class that manages the parser, task list and UI
 */
public class King {
    // Initialise UI, Storage, Parser and TaskList
    private KingUI kingUI = new KingUI();
    private KingParser kingParser = new KingParser("");
    private KingTaskList kingTaskList = new KingTaskList();

    /**
     * Generates a bot introduction
     *
     * @return Generated bot introduction on start of bot
     */
    public String getIntroduction() {
        return kingUI.showIntroduction();
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @return Generated response to user command
     */
    public String getResponse(String input) {
        kingParser.setNewInput(input);
        try {
            if (kingParser.checkParser(KingParser.Commands.HELP)) {
                // help command - lists the possible commands you can give to the king.King bot
                return kingUI.showHelp();
            } else if (kingParser.checkParser(KingParser.Commands.LIST)) {
                // list command - lists all the current tasks in your task list
                return kingUI.showAllList(kingTaskList.getTasks());
            } else if (kingParser.checkParser(KingParser.Commands.DUE)) {
                // due command - lists all tasks on a certain due date
                return kingUI.showDueList(
                        kingTaskList.getTasks(),
                        LocalDate.parse(kingParser.getDueMatcher().group(1)));
            } else if (kingParser.checkParser(KingParser.Commands.FIND)) {
                // find command - finds all tasks with a certain name
                return kingUI.showFindList(kingTaskList.getTasks(), kingParser.getFindMatcher().group(1).split(" "));
            } else if (kingParser.checkParser(KingParser.Commands.TODO)) {
                // todo command - creates a new todo task
                Todo newTask = new Todo(kingParser.getTodoMatcher().group(1));
                kingTaskList.addTask(newTask);
                return kingUI.showTaskCreate(newTask, kingTaskList.getSize());
            } else if (kingParser.checkParser(KingParser.Commands.DEADLINE)) {
                // deadline command - creates a new deadline task
                Deadline newTask = new Deadline(
                        kingParser.getDeadlineMatcher().group(1),
                        LocalDate.parse(kingParser.getDeadlineMatcher().group(2)));
                kingTaskList.addTask(newTask);
                return kingUI.showTaskCreate(newTask, kingTaskList.getSize());
            } else if (kingParser.checkParser(KingParser.Commands.EVENT)) {
                // event command - creates a new event task
                Event newTask = new Event(
                        kingParser.getEventMatcher().group(1),
                        LocalDate.parse(kingParser.getEventMatcher().group(2)),
                        LocalDate.parse(kingParser.getEventMatcher().group(3)));
                kingTaskList.addTask(newTask);
                return kingUI.showTaskCreate(newTask, kingTaskList.getSize());
            } else if (kingParser.checkParser(KingParser.Commands.MARK)) {
                // mark command - marks a task complete
                int idx = Integer.parseInt(kingParser.getMarkMatcher().group(1)) - 1;
                kingTaskList.markDone(idx);
                return kingUI.showMark(kingTaskList.getTask(idx));
            } else if (kingParser.checkParser(KingParser.Commands.UNMARK)) {
                // unmark command - marks a task incomplete
                int idx = Integer.parseInt(kingParser.getUnmarkMatcher().group(1)) - 1;
                kingTaskList.unmarkDone(idx);
                return kingUI.showUnmark(kingTaskList.getTask(idx));
            } else if (kingParser.checkParser(KingParser.Commands.DELETE)) {
                // delete command - deletes a task
                int idx = Integer.parseInt(kingParser.getDeleteMatcher().group(1)) - 1;
                return kingUI.showDelete(kingTaskList.deleteTask(idx), kingTaskList.getSize());
            } else if (kingParser.checkParser(KingParser.Commands.BYE)) {
                // bye command - end of conversation
                return kingUI.showBye();
            } else {
                return kingUI.showInvalidCommand();
            }
        } catch (KingException e) {
            return kingUI.showError(e);
        } catch (IndexOutOfBoundsException e) {
            return kingUI.showInvalidTask();
        } catch (DateTimeParseException e) {
            return kingUI.showInvalidDateTime();
        }
    }
}
