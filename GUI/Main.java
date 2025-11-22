import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Blood Donation Management ");
        System.out.println("Choose mode:");
        System.out.println("1. Console");
        System.out.println("2. GUI");
        System.out.print("Enter (1/2): ");
        String sel = sc.nextLine().trim();
        if ("1".equals(sel)) {
            ConsoleApp.main(new String[] {});
        } else if ("2".equals(sel)) {
            GuiApp.main(new String[] {});
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
        sc.close();
    }
}