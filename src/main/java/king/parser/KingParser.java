package king.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import king.KingException;

/**
 * Parser for the King that helps with checking which commands have been called based on user input
 */
public class KingParser {
    /**
     * Enumeration of all possible commands for King bot
     */
    public enum Commands {
        HELP,
        LIST,
        DUE,
        FIND,
        TODO,
        DEADLINE,
        EVENT,
        MARK,
        UNMARK,
        DELETE,
        BYE
    }

    private final String helpRegex = "^help$";
    private final String listRegex = "^list$";
    private final String dueRegex = "^due(?:\\s+(.*))?$";
    private final String findRegex = "^find(?:\\s+(.*))?$";
    private final String todoRegex = "^todo(?:\\s+(.*))?$";
    private final String deadlineRegex = "^deadline(?:\\s+(.*?)\\s*(?:/by\\s+(.+))?)?$";
    private final String eventRegex = "^event(?:\\s+(.*?)(?:\\s+/from\\s+(.+?))?(?:\\s+/to\\s+(.+))?)?$";
    private final String markRegex = "^mark(?:\\s+(\\d*))?$";
    private final String unmarkRegex = "^unmark(?:\\s+(\\d*))?$";
    private final String deleteRegex = "^delete(?:\\s+(\\d*))?$";
    private final String endRegex = "^bye$";
    private Matcher helpMatcher;
    private Matcher listMatcher;
    private Matcher dueMatcher;
    private Matcher findMatcher;
    private Matcher todoMatcher;
    private Matcher deadlineMatcher;
    private Matcher eventMatcher;
    private Matcher markMatcher;
    private Matcher unmarkMatcher;
    private Matcher deleteMatcher;
    private Matcher endMatcher;

    private String input;

    /**
     * Instantiates the parser with empty string input
     */
    public KingParser() {
        this("");
    }

    /**
     * Instantiates the parser with a string input that the parser uses to check with the matchers
     *
     * @param input String input for parser to check
     */
    public KingParser(String input) {
        setNewInput(input);
    }

    /**
     * Sets the input for the parser object by recompiling the matchers to check with text
     *
     * @param input String input for parser to check
     */
    public void setNewInput(String input) {
        input = input.strip();
        this.input = input;
        helpMatcher = Pattern.compile(helpRegex).matcher(input);
        listMatcher = Pattern.compile(listRegex).matcher(input);
        dueMatcher = Pattern.compile(dueRegex).matcher(input);
        findMatcher = Pattern.compile(findRegex).matcher(input);
        todoMatcher = Pattern.compile(todoRegex).matcher(input);
        deadlineMatcher = Pattern.compile(deadlineRegex).matcher(input);
        eventMatcher = Pattern.compile(eventRegex).matcher(input);
        markMatcher = Pattern.compile(markRegex).matcher(input);
        unmarkMatcher = Pattern.compile(unmarkRegex).matcher(input);
        deleteMatcher = Pattern.compile(deleteRegex).matcher(input);
        endMatcher = Pattern.compile(endRegex).matcher(input);
    }

    /**
     * Check if a specific command has been called based on the matchers
     *
     * @param command King command to check for
     * @return If command has been called, return true.
     * @throws KingException If matching group is missing certain parts, throw IOError KingException.
     */
    public boolean checkParser(Commands command) throws KingException {
        switch (command) {
        case HELP:
            return helpMatcher.matches();
        case LIST:
            return listMatcher.matches();
        case DUE:
            if (dueMatcher.matches()) {
                if (dueMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE);
                }
                return true;
            }
            return false;
        case FIND:
            if (findMatcher.matches()) {
                if (findMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.FIND_MISSING_SEARCH);
                }
                return true;
            }
            return false;
        case TODO:
            if (todoMatcher.matches()) {
                if (todoMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.MISSING_TASK_DESCRIPTION);
                }
                return true;
            }
            return false;
        case DEADLINE:
            if (deadlineMatcher.matches()) {
                if (deadlineMatcher.group(2) == null) {
                    throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE);
                }
                return true;
            }
            return false;
        case EVENT:
            if (eventMatcher.matches()) {
                if (eventMatcher.group(2) == null && eventMatcher.group(3) == null) {
                    throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE);
                } else if (eventMatcher.group(2) == null) {
                    throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE);
                } else if (eventMatcher.group(3) == null) {
                    throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE);
                }
                return true;
            }
            return false;
        case MARK:
            if (markMatcher.matches()) {
                if (markMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.MARK_MISSING_INDEX);
                }
                return true;
            }
            return false;
        case UNMARK:
            if (unmarkMatcher.matches()) {
                if (unmarkMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.UNMARK_MISSING_INDEX);
                }
                return true;
            }
            return false;
        case DELETE:
            if (deleteMatcher.matches()) {
                if (deleteMatcher.group(1) == null) {
                    throw new KingException(KingException.ErrorMessage.DELETE_MISSING_INDEX);
                }
                return true;
            }
            return false;
        case BYE:
            return endMatcher.matches();
        default:
            return false;
        }
    }

    /**
     * Gets help command matcher
     *
     * @return Help command matcher
     */
    public Matcher getHelpMatcher() {
        return helpMatcher;
    }

    /**
     * Gets list command matcher
     *
     * @return List command matcher
     */
    public Matcher getListMatcher() {
        return listMatcher;
    }

    /**
     * Gets due command matcher
     *
     * @return Due command matcher
     */
    public Matcher getDueMatcher() {
        return dueMatcher;
    }

    /**
     * Gets find command matcher
     *
     * @return Find command matcher
     */
    public Matcher getFindMatcher() {
        return findMatcher;
    }

    /**
     * Gets todo command matcher
     *
     * @return Todo command matcher
     */
    public Matcher getTodoMatcher() {
        return todoMatcher;
    }

    /**
     * Gets deadline command matcher
     *
     * @return Deadline command matcher
     */
    public Matcher getDeadlineMatcher() {
        return deadlineMatcher;
    }

    /**
     * Gets event commmand matcher
     *
     * @return Event command matcher
     */
    public Matcher getEventMatcher() {
        return eventMatcher;
    }

    /**
     * Gets mark command matcher
     *
     * @return Mark command matcher
     */
    public Matcher getMarkMatcher() {
        return markMatcher;
    }

    /**
     * Gets unmark command matcher
     *
     * @return Unmark command matcher
     */
    public Matcher getUnmarkMatcher() {
        return unmarkMatcher;
    }

    /**
     * Gets delete command matcher
     *
     * @return Delete command matcher
     */
    public Matcher getDeleteMatcher() {
        return deleteMatcher;
    }

    /**
     * Gets end command matcher
     *
     * @return End command matcher
     */
    public Matcher getEndMatcher() {
        return endMatcher;
    }
}
