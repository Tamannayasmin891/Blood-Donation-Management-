import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Pattern;

public class UserService {
    protected final Path dataDir;
    protected final Path donorsFile;

    // validators
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z]{4,10}$");
    private static final Pattern GMAIL_PATTERN = Pattern.compile("^.+@gmail\\.com$");
    private static final Pattern PASSWORD_DIGIT = Pattern.compile(".*\\d.*");
    private static final Pattern PASSWORD_SPECIAL = Pattern.compile(".*[^A-Za-z0-9].*");

    public UserService() {
        dataDir = Paths.get("data");
        donorsFile = dataDir.resolve("donors.txt");
        try {
            if (!Files.exists(dataDir))
                Files.createDirectories(dataDir);
            if (!Files.exists(donorsFile))
                Files.createFile(donorsFile);
        } catch (IOException e) {
            System.err.println("Unable to prepare data files: " + e.getMessage());
        }
    }

    protected void validateUsername(String username) {
        if (username == null || !USERNAME_PATTERN.matcher(username).matches()) {
            throw new IllegalArgumentException("Username must be 4-10 letters only (A-Za-z).");
        }
    }

    protected void validateEmail(String email) {
        if (email == null || !GMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email must be a Gmail address ending with @gmail.com.");
        }
    }

    protected void validatePassword(String password) {
        if (password == null || password.length() < 6 ||
                !PASSWORD_DIGIT.matcher(password).matches() ||
                !PASSWORD_SPECIAL.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Password must be >=6 chars, contain at least one digit and one special char.");
        }
    }
}