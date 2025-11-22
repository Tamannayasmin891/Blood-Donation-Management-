import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Home extends JPanel {

    public Home(GuiApp app, DonorService donorService) {

        setLayout(new BorderLayout(6, 6));
        JLabel lbl = new JLabel(
                "<html><center>Welcome to Blood Donation System<br/>Search donors or login/register</center></html>",
                SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        add(lbl, BorderLayout.NORTH);

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

        add(searchPanel, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        JPanel btnPanel = new JPanel();
        JButton btnRegister = new JButton("Register");
        JButton btnLogin = new JButton("Login");
        btnPanel.add(btnRegister);
        btnPanel.add(btnLogin);

        btnRegister.addActionListener(e -> app.cardLayout.show(app.mainPanel, "register"));
        btnLogin.addActionListener(e -> app.cardLayout.show(app.mainPanel, "login"));

        add(btnPanel, BorderLayout.NORTH);
    }
}