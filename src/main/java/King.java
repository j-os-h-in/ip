import java.util.ArrayList;
import java.util.Scanner;

public class King {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm King!");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        String text = scanner.nextLine();

        while (!text.equals("bye")) {
            if (text.equals("list")) {
                System.out.println("    ____________________________________________________________");
                for (int i = 1; i <= list.size(); i++) {
                    System.out.println("     " + i + ". " + list.get(i - 1));
                }
                System.out.println("    ____________________________________________________________");

            }
            else {
                System.out.println("    ____________________________________________________________");
                System.out.println("     added: " + text.toUpperCase() + "!!");
                System.out.println("    ____________________________________________________________");
                list.add(text.toUpperCase() + "!!");
            }
            text = scanner.nextLine();
        }

        scanner.close();
        System.out.println("    ____________________________________________________________");
        System.out.println("     BYEBYE!! Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}