package king;

import java.io.IOException;

/**
 * IOException class for King
 */
public class KingException extends IOException {
    /**
     * Enumeration of all possible IO errors for King
     */
    public enum ErrorMessage {
        INVALID_COMMAND("Invalid command"),
        MISSING_TASK_DESCRIPTION("Error! Missing task description! Type it after todo/deadline/event."),
        FIND_MISSING_SEARCH("Error! Search string is missing from command! Use the format `find [keyword].`"),
        DEADLINE_MISSING_DEADLINE("Error! Deadline is not provided! "
                + "Use the format `deadline [task] /by [date]`"),
        EVENT_MISSING_FROM_TO_DATE("Error! From and to date is missing! "
                + "Use the format `event [task] /from [date] /to [date]`"),
        EVENT_MISSING_FROM_DATE("Error! From date is missing! Use the format `event [task] /from [date] /to [date]`"),
        EVENT_MISSING_TO_DATE("Error! To date is missing! Use the format `event [task] /from [date] /to [date]`"),
        MARK_MISSING_INDEX("Error! No mark index specified!"),
        UNMARK_MISSING_INDEX("Error! No unmark index specified!"),
        DELETE_MISSING_INDEX("Error! No delete index specified!"),

        INVALID_DATABASE("[KingStorage] Invalid data in database.");

        private final String message;

        ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Creates an exception and displays the message of the exception
     *
     * @param errorMessage Error given
     */
    public KingException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
