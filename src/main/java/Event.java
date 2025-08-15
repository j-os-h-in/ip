public class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) throws KingException {
        super(description);

        if (from == null && to == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_TO_DATE); }
        else if (from == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_FROM_DATE); }
        else if (to == null) { throw new KingException(KingException.ErrorMessage.EVENT_MISSING_TO_DATE); }
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
