public class Todo extends Task {
    public Todo(String description) throws KingException{
        super(description);
    }

    @Override
    public String getType() {
        return "TODO";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
