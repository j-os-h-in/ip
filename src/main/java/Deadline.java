public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) throws KingException {
        super(description);
        if (by == null) { throw new KingException(KingException.ErrorMessage.DEADLINE_MISSING_DEADLINE); }
        else {this.by = by;}
    }

    @Override
    public String getType() {
        return "DEADLINE";
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
