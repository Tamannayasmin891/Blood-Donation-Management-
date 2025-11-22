import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JPanel {

    public Dashboard(GuiApp app, DonorService donorService, Donor loggedInDonor) {

        setLayout(new BorderLayout(6, 6));

        JTextArea taProfile = new JTextArea(8, 50);
        taProfile.setEditable(false);
        taProfile.setText(getProfileText(loggedInDonor));

        JPanel searchPanel = new JPanel();
        JTextField tfSearchBG = new JTextField(10);
        JButton btnSearch = new JButton("Search");
        searchPanel.add(new JLabel("Search by Blood Group:"));
        searchPanel.add(tfSearchBG);
        searchPanel.add(btnSearch);

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
                    if (!d.getId().equals(loggedInDonor.getId()))
                        sb.append(String.format("ID:%s | Name:%s | Blood:%s | Loc:%s | Mobile:%s%n",
                                d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile()));
            taResults.setText(sb.toString());
        });

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            app.loggedInDonor = null;
            app.cardLayout.show(app.mainPanel, "home");
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(taProfile, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(taResults), BorderLayout.CENTER);
        add(btnLogout, BorderLayout.SOUTH);
    }

    private String getProfileText(Donor d) {
        return String.format("== Your Profile ==\nID: %s\nUsername: %s\nBlood Group: %s\nLocation: %s\nMobile: %s\n",
                d.getId(), d.getUsername(), d.getBloodGroup(), d.getLocation(), d.getMobile());
    }
}