import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GuiApp {
    private final DonorService donorService = new DonorService();
    JFrame frame;
    JPanel mainPanel;
    CardLayout cardLayout;
    Donor loggedInDonor;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuiApp().createGUI());
    }

    void createGUI() {
        frame = new JFrame("Blood Donation System - GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new Home(this, donorService), "home");
        mainPanel.add(new Register(this, donorService), "register");
        mainPanel.add(new Login(this, donorService), "login");

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
        cardLayout.show(mainPanel, "home");
    }

    void showDashboard() {
        mainPanel.add(new Dashboard(this, donorService, loggedInDonor), "dashboard");
        cardLayout.show(mainPanel, "dashboard");
    }
}