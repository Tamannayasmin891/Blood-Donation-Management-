
import java.util.List;
import java.util.Scanner;

public class SearchHandler {
    private final Scanner sc = new Scanner(System.in);
    private final DonorService donorService = new DonorService();

    public void search(Donor loggedInDonor) {
        System.out.print("Enter Blood Group to search: ");
        String bg = sc.nextLine();
        List<Donor> donors = donorService.searchByBloodGroup(bg);
        System.out.println("=== Donors with Blood Group " + bg + " ===");
        if (donors.isEmpty()) {
            System.out.println("No donors found.");
        } else {
            for (Donor d : donors) {
                if (loggedInDonor != null && d.getId().equals(loggedInDonor.getId())) continue;
                System.out.printf("ID: %s | Name: %s | Blood: %s | Location: %s | Mobile: %s%n",
                        d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile());
            }
        }
    }
}