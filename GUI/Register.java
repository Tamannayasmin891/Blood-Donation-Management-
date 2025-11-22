import javax.swing.*;
import java.awt.*;

public class Register extends JPanel {

    public Register(GuiApp app, DonorService donorService) {

        setLayout(new GridLayout(0, 1, 4, 4));
        JTextField tfUser = new JTextField();
        JTextField tfEmail = new JTextField();
        JPasswordField pf = new JPasswordField();
        JTextField tfBlood = new JTextField();
        JTextField tfLoc = new JTextField();
        JTextField tfMobile = new JTextField();

        add(new JLabel("Username (4-10 letters):"));
        add(tfUser);
        add(new JLabel("Email (must end @gmail.com):"));
        add(tfEmail);
        add(new JLabel("Password (>=6, 1 digit, 1 special):"));
        add(pf);
        add(new JLabel("Blood Group:"));
        add(tfBlood);
        add(new JLabel("Location:"));
        add(tfLoc);
        add(new JLabel("Mobile:"));
        add(tfMobile);

        JButton btnRegister = new JButton("Register");
        add(btnRegister);
        JLabel lblMsg = new JLabel(" ");
        add(lblMsg);

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
        btnBack.addActionListener(e -> app.cardLayout.show(app.mainPanel, "home"));
        add(btnBack);
    }
}