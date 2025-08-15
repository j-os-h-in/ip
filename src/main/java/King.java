import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class King {
    public static void main(String[] args) {
        // Initialise bot
        String spacer = "    ";
        System.out.println(spacer + "____________________________________________________________");
        System.out.println(spacer + "Hello! I'm King!");
        System.out.println(spacer + "What can I do for you?");
        System.out.println(spacer + "____________________________________________________________");

        // Initialise input scanner
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();
        String text = scanner.nextLine();

        String listRegex = "^list$";
        String todoRegex = "^todo\\s+(.+)$";
        String deadlineRegex ="^deadline\\s+(.+?)\\s+/by\\s+(.+)$";
        String eventRegex = "^event\\s+(.+?)\\s+/from\\s+(.+?)\\s+/to\\s+(.+)$";
        String markRegex = "^mark\\s+(\\d+)$";
        String unmarkRegex = "^unmark\\s+(\\d+)$";
        String endRegex = "^bye$";

        Matcher listMatcher = Pattern.compile(listRegex).matcher(text);
        Matcher todoMatcher = Pattern.compile(todoRegex).matcher(text);
        Matcher deadlineMatcher = Pattern.compile(deadlineRegex).matcher(text);
        Matcher eventMatcher = Pattern.compile(eventRegex).matcher(text);
        Matcher markMatcher = Pattern.compile(markRegex).matcher(text);
        Matcher unmarkMatcher = Pattern.compile(unmarkRegex).matcher(text);
        Matcher endMatcher = Pattern.compile(endRegex).matcher(text);

        // Loop to get user input
        while (!endMatcher.matches()) {
            System.out.println(spacer + "____________________________________________________________");
            try {
                if (listMatcher.matches()) {
                    System.out.println(spacer + "Here are the tasks in your list:");
                    for (int i = 1; i <= list.size(); i++) {
                        System.out.println(spacer + " " + i + ". " + list.get(i - 1));
                    }
                }
                else if (markMatcher.matches()){
                    int idx = Integer.parseInt(markMatcher.group(1));
                    list.get(idx - 1).markDone();
                    System.out.println(spacer + " NICE!! I've marked this task as done:");
                    System.out.println(spacer + "   " + list.get(idx - 1));
                }
                else if (unmarkMatcher.matches()){
                    int idx = Integer.parseInt(unmarkMatcher.group(1));
                    list.get(idx - 1).unmarkDone();
                    System.out.println(spacer + " OK :( I've marked this task as not done yet");
                    System.out.println(spacer + "   " + list.get(idx - 1));
                }
                else if (todoMatcher.matches()){
                    list.add(new Todo(
                            todoMatcher.group(1)
                    ));
                    System.out.println(spacer + " Ok! I've added this todo:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else if (deadlineMatcher.matches()){
                    list.add(new Deadline(
                            deadlineMatcher.group(1),
                            deadlineMatcher.group(2)));
                    System.out.println(spacer + " Ok! I've added this deadline:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else if (eventMatcher.matches()){
                    list.add(new Event(
                            eventMatcher.group(1),
                            eventMatcher.group(2),
                            eventMatcher.group(3)
                    ));
                    System.out.println(spacer + " Ok! I've added this event:");
                    System.out.println(spacer + "   " + list.getLast());
                    System.out.println(spacer + " Now you have " + list.size() + " tasks in the list.");
                }
                else {
                    System.out.println(spacer + " Error! Invalid command.");
                }
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println(spacer + " Error! Task does not exist... ");
            }
            finally {
                System.out.println(spacer + "____________________________________________________________");
                text = scanner.nextLine().strip();
                listMatcher = Pattern.compile(listRegex).matcher(text);
                todoMatcher = Pattern.compile(todoRegex).matcher(text);
                deadlineMatcher = Pattern.compile(deadlineRegex).matcher(text);
                eventMatcher = Pattern.compile(eventRegex).matcher(text);
                markMatcher = Pattern.compile(markRegex).matcher(text);
                unmarkMatcher = Pattern.compile(unmarkRegex).matcher(text);
                endMatcher = Pattern.compile(endRegex).matcher(text);
            }
        }

        scanner.close();
        System.out.println(spacer + "____________________________________________________________");
        System.out.println(spacer + " BYE BYE!! Hope to see you again soon!");
        System.out.println(spacer + "____________________________________________________________");
    }
}