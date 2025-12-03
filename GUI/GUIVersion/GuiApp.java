import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuiApp {
    private final DonorService donorService = new DonorService();
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private Donor loggedInDonor;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiApp().createGUI());
    }

    private void createGUI() {
        frame = new JFrame("Blood Donation System - GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(buildHomePanel(), "home");
        mainPanel.add(buildRegisterPanel(), "register");
        mainPanel.add(buildLoginPanel(), "login");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        cardLayout.show(mainPanel, "home");
    }

    // Home panel: shows search + buttons for register/login
    private JPanel buildHomePanel() {
        JPanel panel = new JPanel(new BorderLayout(6, 6));
        JLabel lbl = new JLabel(
                "<html><center>Welcome to Blood Donation System<br/>Search donors or login/register</center></html>",
                SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lbl, BorderLayout.NORTH);

        // Search panel accessible to all
        JPanel searchPanel = new JPanel();
        JTextField tfSearchBG = new JTextField(10);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Search by Blood Group:"));
        searchPanel.add(tfSearchBG);
        searchPanel.add(btnSearch);

        JTextArea taResults = new JTextArea(15, 50);
        taResults.setEditable(false);
        JScrollPane scroll = new JScrollPane(taResults);

        btnSearch.addActionListener(e -> {
            String bg = tfSearchBG.getText().trim();
            List<Donor> res = donorService.searchByBloodGroup(bg);
            StringBuilder sb = new StringBuilder();
            sb.append("=== Donors with Blood Group: ").append(bg).append(" ===\n");
            if (res.isEmpty())
                sb.append("No donors found.\n");
            else
                for (Donor d : res)
                    sb.append(String.format("ID:%s | Name:%s | Blood:%s | Loc:%s | Mobile:%s%n",
                            d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile()));
            taResults.setText(sb.toString());
        });

        panel.add(searchPanel, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);

        // Buttons for Register/Login
        JPanel btnPanel = new JPanel();
        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Login");
        btnPanel.add(btnRegister);
        btnPanel.add(btnLogin);

        btnRegister.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        btnLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));

        panel.add(btnPanel, BorderLayout.NORTH);

        return panel;
    }

    // Register panel
    private JPanel buildRegisterPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        JTextField tfUser = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField pf = new JPasswordField();
        JTextField tfBlood = new JTextField();
        JTextField tfLoc = new JTextField();
        JTextField tfMobile = new JTextField();

        panel.add(new JLabel("Username (4-10 letters):"));
        panel.add(tfUser);
        panel.add(new JLabel("Email (must end @gmail.com):"));
        panel.add(tfEmail);
        panel.add(new JLabel("Password (>=6, 1 digit, 1 special):"));
        panel.add(pf);
        panel.add(new JLabel("Blood Group:"));
        panel.add(tfBlood);
        panel.add(new JLabel("Location:"));
        panel.add(tfLoc);
        panel.add(new JLabel("Mobile:"));
        panel.add(tfMobile);

        JButton btnRegister = new JButton("Register");
        panel.add(btnRegister);
        JLabel lblMsg = new JLabel(" ");
        panel.add(lblMsg);

        btnRegister.addActionListener(e -> {
            try {
                Donor d = donorService.registerDonor(
                        tfUser.getText().trim(),
                        tfEmail.getText().trim(),
                        new String(pf.getPassword()),
                        tfBlood.getText().trim(),
                        tfLoc.getText().trim(),
                        tfMobile.getText().trim());
                lblMsg.setText("Registered! ID: " + d.getId());
                tfUser.setText("");
                tfEmail.setText("");
                pf.setText("");
                tfBlood.setText("");
                tfLoc.setText("");
                tfMobile.setText("");
            } catch (Exception ex) {
                lblMsg.setText("Error: " + ex.getMessage());
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        panel.add(btnBack);

        return panel;
    }

    // Login panel
    private JPanel buildLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 4, 4));
        JTextField tfUE = new JTextField();
        JPasswordField pf = new JPasswordField();
        panel.add(new JLabel("Username or Email:"));
        panel.add(tfUE);
        panel.add(new JLabel("Password:"));
        panel.add(pf);

        JButton btnLogin = new JButton("Login");
        panel.add(btnLogin);
        JLabel lblMsg = new JLabel(" ");
        panel.add(lblMsg);

        btnLogin.addActionListener(e -> {
            loggedInDonor = donorService.login(tfUE.getText().trim(), new String(pf.getPassword()));
            if (loggedInDonor == null) {
                lblMsg.setText("Login failed. Check credentials.");
            } else {
                showDashboard();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> cardLayout.show(mainPanel, "home"));
        panel.add(btnBack);

        return panel;
    }

    // Dashboard for logged-in user (profile + search)
    private void showDashboard() {
        JPanel dash = new JPanel(new BorderLayout(6, 6));

        // Profile text
        JTextArea taProfile = new JTextArea(8, 50);
        taProfile.setEditable(false);
        taProfile.setText(getProfileText(loggedInDonor));

        // Search panel
        JPanel searchPanel = new JPanel();
        JTextField tfSearchBG = new JTextField(10);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Search by Blood Group:"));
        searchPanel.add(tfSearchBG);
        searchPanel.add(btnSearch);

        // Results text
        JTextArea taResults = new JTextArea(10, 50);
        taResults.setEditable(false);

        btnSearch.addActionListener(e -> {
            String bg = tfSearchBG.getText().trim();
            List<Donor> res = donorService.searchByBloodGroup(bg);
            StringBuilder sb = new StringBuilder();
            sb.append("=== Donors with Blood Group: ").append(bg).append(" ===\n");
            if (res.isEmpty())
                sb.append("No donors found.\n");
            else
                for (Donor d : res)
                    if (!d.getId().equals(loggedInDonor.getId())) // exclude self
                        sb.append(String.format("ID:%s | Name:%s | Blood:%s | Loc:%s | Mobile:%s%n",
                                d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile()));
            taResults.setText(sb.toString());
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            loggedInDonor = null;
            cardLayout.show(mainPanel, "home");
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(taProfile, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        dash.add(topPanel, BorderLayout.NORTH);
        dash.add(new JScrollPane(taResults), BorderLayout.CENTER);
        dash.add(btnLogout, BorderLayout.SOUTH);

        mainPanel.add(dash, "dashboard");
        cardLayout.show(mainPanel, "dashboard");
    }

    private String getProfileText(Donor d) {
        return String.format("== Your Profile ==\nID: %s\nUsername: %s\nBlood Group: %s\nLocation: %s\nMobile: %s\n",
                d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile());
    }
}