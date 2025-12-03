import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    private static final DonorService donorService = new DonorService();
    private static Donor loggedInDonor;
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Blood Donation System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Search by Blood Group");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    search();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void register() {
        try {
            System.out.print("Username (4-10 letters): ");
            String user = sc.nextLine();
            System.out.print("Email (@gmail.com): ");
            String email = sc.nextLine();
            System.out.print("Password (>=6, 1 digit, 1 special): ");
            String pw = sc.nextLine();
            System.out.print("Blood Group: ");
            String bg = sc.nextLine();
            System.out.print("Location: ");
            String loc = sc.nextLine();
            System.out.print("Mobile: ");
            String mobile = sc.nextLine();

            Donor d = donorService.registerDonor(user, email, pw, bg, loc, mobile);
            System.out.println("Registered successfully! ID: " + d.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Username or Email: ");
        String ue = sc.nextLine();
        System.out.print("Password: ");
        String pw = sc.nextLine();
        loggedInDonor = donorService.login(ue, pw);
        if (loggedInDonor == null) {
            System.out.println("Login failed! Check credentials.");
        } else {
            System.out.println("\n=== Welcome " + loggedInDonor.getUsername() + " ===");
            showProfile();
        }
    }

    private static void showProfile() {
        System.out.println("Your Profile:");
        System.out.println("ID: " + loggedInDonor.getId());
        System.out.println("Username: " + loggedInDonor.getUsername());
        System.out.println("Blood Group: " + loggedInDonor.getBloodGroup());
        System.out.println("Location: " + loggedInDonor.getLocation());
        System.out.println("Mobile: " + loggedInDonor.getMobile());
    }

    private static void search() {
        System.out.print("Enter Blood Group to search: ");
        String bg = sc.nextLine();
        List<Donor> donors = donorService.searchByBloodGroup(bg);
        System.out.println("=== Donors with Blood Group " + bg + " ===");
        if (donors.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            for (Donor d : donors) {
                // if logged-in, exclude self
                if (loggedInDonor != null && d.getId().equals(loggedInDonor.getId()))
                    continue;
                System.out.printf("ID: %s | Name: %s | Blood: %s | Location: %s | Mobile: %s%n",
                        d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile());
            }
        }
    }
}