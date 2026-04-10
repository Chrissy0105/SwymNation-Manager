package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientDashboardFrame extends JFrame {

    private JButton paymentBtn;
    private JButton scheduleBtn;
    private JButton progressBtn;
    private JButton notificationsBtn;
    private JButton logoutBtn;

    private String clientEmail;

    public ClientDashboardFrame(String clientEmail) {
        this.clientEmail = clientEmail;

        UIHelper.setupFrame(this, "Client Dashboard");

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel header = UIHelper.createHeader("Client Dashboard", UIHelper.PURPLE);
        JLabel subHeader = UIHelper.createSubHeader(
                "Welcome — view your classes, payments, notifications, and progress.");

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
        stats.add(UIHelper.createStatCard("Role", "Client", UIHelper.PURPLE));
        stats.add(UIHelper.createStatCard("Schedule", "Linked to DB", UIHelper.GREEN));
        stats.add(UIHelper.createStatCard("Notifications", "Live", UIHelper.PRIMARY_BLUE));

        JPanel card = UIHelper.createGridCardPanel(2, 3, 900, 340);

        paymentBtn = UIHelper.createDashboardTile("Make Payment 💳", "Submit a payment", UIHelper.PURPLE);
        scheduleBtn = UIHelper.createDashboardTile("View Schedule 📅", "See upcoming classes", UIHelper.PRIMARY_BLUE);
        progressBtn = UIHelper.createDashboardTile("View Progress 📈", "See your progress", UIHelper.TEAL);
        notificationsBtn = UIHelper.createDashboardTile("Notifications 🔔", "Read payment and class alerts",
                UIHelper.GREEN);
        logoutBtn = UIHelper.createDashboardTile("Logout ↩", "Return to login", UIHelper.GRAY);

        paymentBtn.addActionListener(new PaymentListener());
        scheduleBtn.addActionListener(new ScheduleListener());
        progressBtn.addActionListener(new ProgressListener());
        notificationsBtn.addActionListener(new NotificationsListener());
        logoutBtn.addActionListener(new LogoutListener());

        card.add(paymentBtn);
        card.add(scheduleBtn);
        card.add(progressBtn);
        card.add(notificationsBtn);
        card.add(logoutBtn);

        content.add(stats);
        content.add(Box.createVerticalStrut(18));
        content.add(card);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class PaymentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PaymentFrame("Client");
            dispose();
        }
    }

    private class ScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewScheduleFrame(clientEmail);
            dispose();
        }
    }

    private class ProgressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ClientProgressFrame(clientEmail);
            dispose();
        }
    }

    private class NotificationsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new NotificationsFrame(clientEmail);
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