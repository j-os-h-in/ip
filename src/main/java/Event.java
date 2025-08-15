public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) throws KingException {
        super(description);

        if (from == null && to == null) { throw new KingException("Error! From date and to date is missing!"); }
        else if (from == null) { throw new KingException("Error! From date is missing!"); }
        else if (to == null) { throw new KingException("Error! To date is missing!"); }
        else {
            this.from = from;
            this.to = to;
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
