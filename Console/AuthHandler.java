
import java.util.Scanner;

public class AuthHandler {
    private final Scanner sc = new Scanner(System.in);
    private final DonorService donorService = new DonorService();
    private Donor loggedInDonor;

    public Donor getLoggedInDonor() {
        return loggedInDonor;
    }

    public void register() {
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

    public void login() {
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

    private void showProfile() {
        System.out.println("Your Profile:");
        System.out.println("ID: " + loggedInDonor.getId());
        System.out.println("Username: " + loggedInDonor.getUsername());
        System.out.println("Blood Group: " + loggedInDonor.getBloodGroup());
        System.out.println("Location: " + loggedInDonor.getLocation());
        System.out.println("Mobile: " + loggedInDonor.getMobile());
    }
}