import java.io.*;
import java.nio.file.*;
import java.util.*;

/*
 DonorService stores donors in data/donors.txt (pipe-separated).
 It ensures short unique IDs by scanning existing file on load and keeping lastNumericId.
*/
public class DonorService extends UserService {
    private final Path file;
    private final List<Donor> donors = new ArrayList<>();
    private int lastNumericId = 0; // numeric part of last ID (e.g., 1 for BD001)

    public DonorService() {
        super();
        file = super.donorsFile;
        loadFromFile();
    }

    // register donor with validation; returns created Donor
    public Donor registerDonor(String username, String email, String password,
            String bloodGroup, String location, String mobile) throws IOException {
        // validations (throws IllegalArgumentException on fail)
        validateUsername(username);
        validateEmail(email);
        validatePassword(password);
        if (bloodGroup == null || bloodGroup.trim().isEmpty())
            throw new IllegalArgumentException("Blood group required.");
        if (location == null || location.trim().isEmpty())
            throw new IllegalArgumentException("Location required.");
        if (mobile == null || mobile.trim().isEmpty())
            throw new IllegalArgumentException("Mobile required.");

        // ensure email or username unique
        for (Donor d : donors) {
            if (d.getEmail().equalsIgnoreCase(email))
                throw new IllegalArgumentException("Email already registered.");
            if (d.getUsername().equalsIgnoreCase(username))
                throw new IllegalArgumentException("Username already taken.");
        }

        String id = generateNextId();
        Donor donor = new Donor(id, username, email, password, bloodGroup, location, mobile);
        donors.add(donor);
        appendToFile(donor);
        return donor;
    }

    // generate ID like BD001, BD002
    private synchronized String generateNextId() {
        lastNumericId++;
        return String.format("BD%03d", lastNumericId);
    }

    // append single donor line (no overwrite)
    private void appendToFile(Donor d) throws IOException {
        String line = d.toDataString();
        Files.write(file, (line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
    }

    // load all donors from file and compute lastNumericId
    private void loadFromFile() {
        donors.clear();
        try {
            List<String> lines = Files.readAllLines(file);
            for (String ln : lines) {
                if (ln.trim().isEmpty())
                    continue;
                String[] p = ln.split("\\|", -1);
                if (p.length < 7)
                    continue; // ignore malformed
                String id = p[0];
                String username = p[1];
                String email = p[2];
                String password = p[3];
                String blood = p[4];
                String location = p[5];
                String mobile = p[6];
                donors.add(new Donor(id, username, email, password, blood, location, mobile));
                // parse numeric part
                try {
                    if (id.startsWith("BD")) {
                        int num = Integer.parseInt(id.substring(2));
                        if (num > lastNumericId)
                            lastNumericId = num;
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load donors: " + e.getMessage());
        }
    }

    // Return all donors (unmodifiable view)
    public List<Donor> getAllDonors() {
        return Collections.unmodifiableList(donors);
    }

    // Search donors by blood group (case-insensitive)
    public List<Donor> searchByBloodGroup(String bloodGroup) {
        if (bloodGroup == null)
            return Collections.emptyList();
        String q = bloodGroup.trim().toLowerCase();
        List<Donor> res = new ArrayList<>();
        for (Donor d : donors) {
            if (d.getBloodGroup() != null && d.getBloodGroup().trim().toLowerCase().equals(q))
                res.add(d);
        }
        return res;
    }

    // Search donors by location (case-insensitive)
    public List<Donor> searchByLocation(String location) {
        if (location == null)
            return Collections.emptyList();
        String q = location.trim().toLowerCase();
        List<Donor> res = new ArrayList<>();
        for (Donor d : donors) {
            if (d.getLocation() != null && d.getLocation().trim().toLowerCase().equals(q))
                res.add(d);
        }
        return res;
    }

    // simple login (not used heavily here but available)
    public Donor login(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || password == null)
            return null;
        for (Donor d : donors) {
            if ((d.getUsername().equalsIgnoreCase(usernameOrEmail) || d.getEmail().equalsIgnoreCase(usernameOrEmail))
                    && d.getPassword().equals(password)) {
                return d;
            }
        }
        return null;
    }
}