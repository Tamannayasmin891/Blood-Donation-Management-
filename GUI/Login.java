import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {

    public Login(GuiApp app, DonorService donorService) {

        setLayout(new GridLayout(0, 1, 4, 4));

        JTextField tfUE = new JTextField();
        JPasswordField pf = new JPasswordField();
        add(new JLabel("Username or Email:"));
        add(tfUE);
        add(new JLabel("Password:"));
        add(pf);

        JButton btnLogin = new JButton("Login");
        add(btnLogin);

        JLabel lblMsg = new JLabel(" ");
        add(lblMsg);

        btnLogin.addActionListener(e -> {
            app.loggedInDonor = donorService.login(tfUE.getText().trim(), new String(pf.getPassword()));
            if (app.loggedInDonor == null) {
                lblMsg.setText("Login failed. Check credentials.");
            } else {
                app.showDashboard();
            }
        });

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> app.cardLayout.show(app.mainPanel, "home"));
        add(btnBack);
    }
}