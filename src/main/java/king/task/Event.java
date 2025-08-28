package king.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import king.KingException;

/**
 * Task with from date and to date
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;

    /**
     * Instantiates an event based on the description and period of the task.
     * If no start / end date is provided, throws a missing date exception.
     *
     * @param description Description of the task.
     * @param from        Start date of the event.
     * @param to          End date of the event.
     * @throws KingException Error in creation of task.
     */
    public Event(String description, LocalDate from, LocalDate to) throws KingException {
        super(description);

        if (from == null && to == null) {
            throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE);
        } else if (from == null) {
            throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE);
        } else if (to == null) {
            throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE);
        } else {
            this.from = from;
            this.to = to;
        }
    }

    /**
     * Returns the start date of the event.
     *
     * @return Start date.
     */
    public LocalDate getFrom() {
        return this.from;
    }

    /**
     * Returns the end date of the event.
     *
     * @return End date.
     */
    public LocalDate getTo() {
        return this.to;
    }

    /**
     * Sets the start date of the event.
     *
     * @param from king.task.Event start date.
     */
    public void setFrom(LocalDate from) {
        this.from = from;
    }

    /**
     * Sets the end date of the event.
     *
     * @param to king.task.Event end date.
     */
    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override
    public Type getType() {
        return Type.EVENT;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern("d MMM yyyy"))
                + " to: " + to.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ")";
    }
}
