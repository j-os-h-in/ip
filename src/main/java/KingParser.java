import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KingParser {
    public enum Commands {
        HELP,
        LIST,
        DUE,
        TODO,
        DEADLINE,
        EVENT,
        MARK,
        UNMARK,
        DELETE,
        BYE
    }
    private final String helpRegex        = "^help$";
    private final String listRegex        = "^list$";
    private final String dueRegex         = "^due(?:\\s+(.*))?$";
    private final String todoRegex        = "^todo(?:\\s+(.*))?$";
    private final String deadlineRegex    = "^deadline(?:\\s+(.*?)\\s*(?:/by\\s+(.+))?)?$";
    private final String eventRegex       = "^event(?:\\s+(.*?)(?:\\s+/from\\s+(.+?))?(?:\\s+/to\\s+(.+))?)?$";
    private final String markRegex        = "^mark(?:\\s+(\\d*))?$";
    private final String unmarkRegex      = "^unmark(?:\\s+(\\d*))?$";
    private final String deleteRegex      = "^delete(?:\\s+(\\d*))?$";
    private final String endRegex         = "^bye$";
    private Matcher helpMatcher, listMatcher, dueMatcher, todoMatcher, deadlineMatcher, eventMatcher, markMatcher, unmarkMatcher, deleteMatcher, endMatcher;

    private String input;

    public KingParser(String input) {
        this.input = input;
        helpMatcher     = Pattern.compile(helpRegex).matcher(input);
        listMatcher     = Pattern.compile(listRegex).matcher(input);
        dueMatcher      = Pattern.compile(dueRegex).matcher(input);
        todoMatcher     = Pattern.compile(todoRegex).matcher(input);
        deadlineMatcher = Pattern.compile(deadlineRegex).matcher(input);
        eventMatcher    = Pattern.compile(eventRegex).matcher(input);
        markMatcher     = Pattern.compile(markRegex).matcher(input);
        unmarkMatcher   = Pattern.compile(unmarkRegex).matcher(input);
        deleteMatcher   = Pattern.compile(deleteRegex).matcher(input);
        endMatcher      = Pattern.compile(endRegex).matcher(input);
    }

    public void setNewInput(String input) {
        this.input = input;
        helpMatcher     = Pattern.compile(helpRegex).matcher(input);
        listMatcher     = Pattern.compile(listRegex).matcher(input);
        dueMatcher      = Pattern.compile(dueRegex).matcher(input);
        todoMatcher     = Pattern.compile(todoRegex).matcher(input);
        deadlineMatcher = Pattern.compile(deadlineRegex).matcher(input);
        eventMatcher    = Pattern.compile(eventRegex).matcher(input);
        markMatcher     = Pattern.compile(markRegex).matcher(input);
        unmarkMatcher   = Pattern.compile(unmarkRegex).matcher(input);
        deleteMatcher   = Pattern.compile(deleteRegex).matcher(input);
        endMatcher      = Pattern.compile(endRegex).matcher(input);
    }

    public boolean checkParser(Commands command) throws KingException {
        switch (command) {
        case HELP: return helpMatcher.matches();
        case LIST: return listMatcher.matches();
        case DUE:
            if (dueMatcher.matches()) {
                if (dueMatcher.group(1) == null) throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE);
                return true;
            }
            return false;
        case TODO:
            if (todoMatcher.matches()) {
              if (todoMatcher.group(1) == null) throw new KingException(KingException.ErrorMessage.MISSING_TASK_DESCRIPTION);
              return true;
            }
            return false;
        case DEADLINE:
            if (deadlineMatcher.matches()) {
              if (deadlineMatcher.group(2) == null) throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE);
              return true;
            }
            return false;
        case EVENT:
            if (eventMatcher.matches()) {
                if (eventMatcher.group(2) == null && eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE); }
                else if (eventMatcher.group(2) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE); }
                else if (eventMatcher.group(3) == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE); }
                return true;
            }
            return false;
        case MARK:
            if (markMatcher.matches()) {
                if (markMatcher.group(1) == null) throw new KingException(KingException.ErrorMessage.MARK_MISSING_INDEX);
                return true;
            }
            return false;
        case UNMARK:
            if (unmarkMatcher.matches()) {
                if (unmarkMatcher.group(1) == null) throw new KingException(KingException.ErrorMessage.UNMARK_MISSING_INDEX);
                return true;
            }
            return false;
        case DELETE:
            if (deleteMatcher.matches()) {
                if (deleteMatcher.group(1) == null) throw new KingException(KingException.ErrorMessage.DELETE_MISSING_INDEX);
                return true;
            }
            return false;
        case BYE: return endMatcher.matches();
        default: return false;
        }
    }

    public Matcher getHelpMatcher() {
        return helpMatcher;
    }

    public Matcher getListMatcher() {
        return listMatcher;
    }

    public Matcher getDueMatcher() {
        return dueMatcher;
    }

    public Matcher getTodoMatcher() {
        return todoMatcher;
    }

    public Matcher getDeadlineMatcher() {
        return deadlineMatcher;
    }

    public Matcher getEventMatcher() {
        return eventMatcher;
    }

    public Matcher getMarkMatcher() {
        return markMatcher;
    }

    public Matcher getUnmarkMatcher() {
        return unmarkMatcher;
    }

    public Matcher getDeleteMatcher() {
        return deleteMatcher;
    }

    public String getEndRegex() {
        return endRegex;
    }
}
