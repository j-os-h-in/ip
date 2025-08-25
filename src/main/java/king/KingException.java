package king;

import java.io.IOException;

public class KingException extends IOException {
    public enum ErrorMessage {
        INVALID_COMMAND("Invalid command"),
        MISSING_TASK_DESCRIPTION("Error! Missing task description! Type it after todo/deadline/event."),

        DEADLINE_MISSING_DEADLINE("Error! king.task.Deadline is not provided! Use the format `deadline [task] /by [date]`"),

        EVENT_MISSING_FROM_TO_DATE("Error! From and to date is missing! Use the format `event [task] /from [date] /to [date]`"),
        EVENT_MISSING_FROM_DATE("Error! From date is missing! Use the format `event [task] /from [date] /to [date]`"),
        EVENT_MISSING_TO_DATE("Error! To date is missing! Use the format `event [task] /from [date] /to [date]`"),
        MARK_MISSING_INDEX("Error! No mark index specified!"),
        UNMARK_MISSING_INDEX("Error! No unmark index specified!"),
        DELETE_MISSING_INDEX("Error! No delete index specified!"),

        INVALID_DATABASE("[king.storage.KingStorage] Invalid data in database.");

        private final String message;

        ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    public KingException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }
}
