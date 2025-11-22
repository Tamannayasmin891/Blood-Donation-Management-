import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final AuthHandler authHandler = new AuthHandler();
    private final SearchHandler searchHandler = new SearchHandler();

    public void showMainMenu() {
        while (true) {
            System.out.println("\n=== Blood Donation System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Search by Blood Group");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1" -> authHandler.register();
                case "2" -> authHandler.login();
                case "3" -> searchHandler.search(authHandler.getLoggedInDonor());
                case "4" -> System.exit(0);
                default -> System.out.println("Invalid option!");
            }
        }
    }
}