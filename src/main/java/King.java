import java.util.Scanner;

public class King {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________");
        System.out.println("     Hello! I'm King!");
        System.out.println("     What can I do for you?");
        System.out.println("    ____________________________________________________________");

        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        while (!text.equals("bye")) {
            System.out.println("    ____________________________________________________________");
            System.out.println("    " + text.toUpperCase() + "!!");
            System.out.println("    ____________________________________________________________");
            text = scanner.nextLine();
        }

        scanner.close();
        System.out.println("    ____________________________________________________________");
        System.out.println("     BYEBYE!! Hope to see you again soon!");
        System.out.println("    ____________________________________________________________");
    }
}