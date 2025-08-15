public class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) throws KingException {
        super(description);
        if (by == null) { throw new KingException("Error! Deadline is not provided! Use the format `deadline [task] /by [date]`"); }
        else {this.by = by;}
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
