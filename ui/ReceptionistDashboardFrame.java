package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReceptionistDashboardFrame extends JFrame {

    private JButton registerClientBtn;
    private JButton paymentBtn;
    private JButton notificationBtn;
    private JButton logoutBtn;

    public ReceptionistDashboardFrame() {
        UIHelper.setupFrame(this, "Receptionist Dashboard");

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel header = UIHelper.createHeader("Receptionist Dashboard", UIHelper.TEAL);
        JLabel subHeader = UIHelper.createSubHeader(
                "Welcome, Receptionist — handle client registration, payments, and communication.");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(UIHelper.BACKGROUND);
        topPanel.add(header, BorderLayout.NORTH);
        topPanel.add(subHeader, BorderLayout.SOUTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UIHelper.BACKGROUND);
        content.setBorder(new javax.swing.border.EmptyBorder(24, 24, 24, 24));

        JPanel stats = new JPanel(new GridLayout(1, 3, 16, 16));
        stats.setBackground(UIHelper.BACKGROUND);
        stats.add(UIHelper.createStatCard("Role", "Receptionist", UIHelper.TEAL));
        stats.add(UIHelper.createStatCard("Client Support", "Active", UIHelper.GREEN));
        stats.add(UIHelper.createStatCard("Payments", "Ready", UIHelper.PRIMARY_BLUE));

        JPanel card = UIHelper.createGridCardPanel(2, 2, 820, 340);

        registerClientBtn = UIHelper.createDashboardTile("Add Client 👤", "Create new client accounts", UIHelper.GREEN);
        paymentBtn = UIHelper.createDashboardTile("Process Payment 💳", "Record client payment", UIHelper.TEAL);
        notificationBtn = UIHelper.createDashboardTile("Send Notification 🔔", "Notify a client",
                UIHelper.PRIMARY_BLUE);
        logoutBtn = UIHelper.createDashboardTile("Logout ↩", "Return to login", UIHelper.GRAY);

        registerClientBtn.addActionListener(new RegisterClientListener());
        paymentBtn.addActionListener(new PaymentListener());
        notificationBtn.addActionListener(new NotificationListener());
        logoutBtn.addActionListener(new LogoutListener());

        card.add(registerClientBtn);
        card.add(paymentBtn);
        card.add(notificationBtn);
        card.add(logoutBtn);

        content.add(stats);
        content.add(Box.createVerticalStrut(18));
        content.add(card);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class RegisterClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterClientFrame("Receptionist");
            dispose();
        }
    }

    private class PaymentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PaymentFrame("Receptionist");
            dispose();
        }
    }

    private class NotificationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SendNotificationFrame("Receptionist");
            dispose();
        }
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginFrame();
            dispose();
        }
    }
}