package king.task;

import king.KingException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDate by;

    /**
     * Instantiates a deadline task based on the description and deadline of the task.
     * If no deadline is provided, throws a missing deadline exception.
     * @param description Description of the task.
     * @param by king.task.Deadline of the task.
     * @throws KingException Error in creation of task.
     */
    public Deadline(String description, LocalDate by) throws KingException {
        super(description);
        if (by == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); }
        else {this.by = by;}
    }

    /**
     * Returns the completion date of the task.
     * @return Date of completion.
     */
    public LocalDate getBy() {
        return this.by;
    }

    /**
     * Sets the deadline of the task.
     * @param by king.task.Task deadline.
     */
    public void setBy(LocalDate by) {
        this.by = by;
    }

    @Override
    public Type getType() {
        return Type.DEADLINE;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("d MMM yyyy")) + ")";
    }
}
