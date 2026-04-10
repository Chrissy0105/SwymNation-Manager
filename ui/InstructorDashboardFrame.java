package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructorDashboardFrame extends JFrame {

    private JButton attendanceBtn;
    private JButton progressBtn;
    private JButton notificationBtn;
    private JButton logoutBtn;

    public InstructorDashboardFrame() {
        UIHelper.setupFrame(this, "Instructor Dashboard");

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel header = UIHelper.createHeader("Instructor Dashboard", UIHelper.ORANGE);
        JLabel subHeader = UIHelper.createSubHeader(
                "Welcome, Instructor — manage attendance, progress, and client updates.");

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
        stats.add(UIHelper.createStatCard("Role", "Instructor", UIHelper.ORANGE));
        stats.add(UIHelper.createStatCard("Attendance", "Available", UIHelper.GREEN));
        stats.add(UIHelper.createStatCard("Progress", "Trackable", UIHelper.PRIMARY_BLUE));

        JPanel card = UIHelper.createGridCardPanel(2, 2, 820, 340);

        attendanceBtn = UIHelper.createDashboardTile("Take Attendance ✅", "Save class attendance", UIHelper.ORANGE);
        progressBtn = UIHelper.createDashboardTile("Update Progress 📈", "Save progress reports", UIHelper.ORANGE);
        notificationBtn = UIHelper.createDashboardTile("Send Notification 🔔", "Notify a client", UIHelper.GREEN);
        logoutBtn = UIHelper.createDashboardTile("Logout ↩", "Return to login", UIHelper.GRAY);

        attendanceBtn.addActionListener(new AttendanceListener());
        progressBtn.addActionListener(new ProgressListener());
        notificationBtn.addActionListener(new NotificationListener());
        logoutBtn.addActionListener(new LogoutListener());

        card.add(attendanceBtn);
        card.add(progressBtn);
        card.add(notificationBtn);
        card.add(logoutBtn);

        content.add(stats);
        content.add(Box.createVerticalStrut(18));
        content.add(card);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class AttendanceListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AttendanceFrame();
            dispose();
        }
    }

    private class ProgressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ProgressReportFrame();
            dispose();
        }
    }

    private class NotificationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SendNotificationFrame("Instructor");
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