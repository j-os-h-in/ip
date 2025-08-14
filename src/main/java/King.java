import java.util.ArrayList;
import java.util.Scanner;

public class King {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm King!");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();
        String text = scanner.nextLine();

        while (!text.equals("bye")) {
            System.out.println("    ____________________________________________________________");
            try {
                if (text.equals("list")) {
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 1; i <= list.size(); i++) {
                        System.out.println("     " + i + ". " + list.get(i - 1));
                    }
                }
                else if (text.strip().matches("mark [0-9]+")){
                    int idx = Integer.parseInt(text.strip().substring(5)) - 1;
                    list.get(idx).markDone();
                    System.out.println("     NICE!! I've marked this task as done:");
                    System.out.println("       " + list.get(idx));
                }
                else if (text.strip().matches("unmark [0-9]+")){
                    int idx = Integer.parseInt(text.strip().substring(7)) - 1;
                    list.get(idx).unmarkDone();
                    System.out.println("     OK :( I've marked this task as not done yet");
                    System.out.println("       " + list.get(idx));
                }
                else {
                    System.out.println("     added: " + text.toUpperCase() + "!!");
                    list.add(new Task(text.toUpperCase() + "!!"));
                }
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("     Error! Task does not exist... ");
            }
            System.out.println("    ____________________________________________________________");
            text = scanner.nextLine();
        }

        scanner.close();
        System.out.println("    ____________________________________________________________");
        System.out.println("     BYE BYE!! Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}