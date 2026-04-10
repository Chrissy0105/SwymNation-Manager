package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboardFrame extends JFrame {

    private JButton registerClientBtn;
    private JButton registerInstructorBtn;
    private JButton registerReceptionistBtn;
    private JButton registerAdminBtn;
    private JButton makePaymentBtn;
    private JButton viewPaymentsBtn;
    private JButton attendanceBtn;
    private JButton progressBtn;
    private JButton sendNotificationBtn;
    private JButton createSessionBtn;
    private JButton assignSessionBtn;
    private JButton viewSessionsBtn;
    private JButton generateReportBtn;
    private JButton logoutBtn;

    public AdminDashboardFrame() {
        UIHelper.setupFrame(this, "Admin Dashboard");

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel header = UIHelper.createHeader("Admin Dashboard", UIHelper.PRIMARY_BLUE);
        JLabel subHeader = UIHelper.createSubHeader(
                "Welcome, Administrator — manage users, sessions, payments, reports, and system activity.");

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
        stats.add(UIHelper.createStatCard("Access Level", "Administrator", UIHelper.PRIMARY_BLUE));
        stats.add(UIHelper.createStatCard("Controls", "Full System", UIHelper.GREEN));
        stats.add(UIHelper.createStatCard("Reports", "Live Database", UIHelper.PURPLE));

        JPanel tileCard = UIHelper.createGridCardPanel(5, 3, 1050, 720);

        registerClientBtn = UIHelper.createDashboardTile("Add Client 👤", "Create a new client profile",
                UIHelper.PRIMARY_BLUE);
        registerInstructorBtn = UIHelper.createDashboardTile("Add Instructor 🏊", "Register teaching staff",
                UIHelper.PRIMARY_BLUE);
        registerReceptionistBtn = UIHelper.createDashboardTile("Add Receptionist 🧾", "Register front desk staff",
                UIHelper.PRIMARY_BLUE);
        registerAdminBtn = UIHelper.createDashboardTile("Add Admin ⚙️", "Create admin access", UIHelper.PRIMARY_BLUE);
        makePaymentBtn = UIHelper.createDashboardTile("Make Payment 💳", "Record a payment", UIHelper.GREEN);
        viewPaymentsBtn = UIHelper.createDashboardTile("Payments 📄", "View payment history", UIHelper.PRIMARY_BLUE);
        attendanceBtn = UIHelper.createDashboardTile("Attendance ✅", "Review attendance records",
                UIHelper.PRIMARY_BLUE);
        progressBtn = UIHelper.createDashboardTile("Progress 📈", "Inspect progress data", UIHelper.PRIMARY_BLUE);
        sendNotificationBtn = UIHelper.createDashboardTile("Notify Client 🔔", "Send a manual notification",
                UIHelper.TEAL);
        createSessionBtn = UIHelper.createDashboardTile("Create Session 📅", "Create a class session", UIHelper.ORANGE);
        assignSessionBtn = UIHelper.createDashboardTile("Assign Session 🗂️", "Assign client to class",
                UIHelper.ORANGE);
        viewSessionsBtn = UIHelper.createDashboardTile("View Sessions 📋", "Review or delete sessions",
                UIHelper.PRIMARY_BLUE);
        generateReportBtn = UIHelper.createDashboardTile("Generate Report 📊", "Create a full system report",
                UIHelper.GREEN);
        logoutBtn = UIHelper.createDashboardTile("Logout ↩", "Return to login", UIHelper.GRAY);

        registerClientBtn.addActionListener(new RegisterClientListener());
        registerInstructorBtn.addActionListener(new RegisterInstructorListener());
        registerReceptionistBtn.addActionListener(new RegisterReceptionistListener());
        registerAdminBtn.addActionListener(new RegisterAdminListener());
        makePaymentBtn.addActionListener(new MakePaymentListener());
        viewPaymentsBtn.addActionListener(new ViewPaymentsListener());
        attendanceBtn.addActionListener(new AttendanceViewListener());
        progressBtn.addActionListener(new ProgressListener());
        sendNotificationBtn.addActionListener(new SendNotificationListener());
        createSessionBtn.addActionListener(new CreateSessionListener());
        assignSessionBtn.addActionListener(new AssignSessionListener());
        viewSessionsBtn.addActionListener(new ViewSessionsListener());
        generateReportBtn.addActionListener(new GenerateReportListener());
        logoutBtn.addActionListener(new LogoutListener());

        tileCard.add(registerClientBtn);
        tileCard.add(registerInstructorBtn);
        tileCard.add(registerReceptionistBtn);
        tileCard.add(registerAdminBtn);
        tileCard.add(makePaymentBtn);
        tileCard.add(viewPaymentsBtn);
        tileCard.add(attendanceBtn);
        tileCard.add(progressBtn);
        tileCard.add(sendNotificationBtn);
        tileCard.add(createSessionBtn);
        tileCard.add(assignSessionBtn);
        tileCard.add(viewSessionsBtn);
        tileCard.add(generateReportBtn);
        tileCard.add(logoutBtn);

        content.add(stats);
        content.add(Box.createVerticalStrut(18));
        content.add(tileCard);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class RegisterClientListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterClientFrame("Admin");
            dispose();
        }
    }

    private class RegisterInstructorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterInstructorFrame();
            dispose();
        }
    }

    private class RegisterReceptionistListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterReceptionistFrame();
            dispose();
        }
    }

    private class RegisterAdminListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterAdminFrame();
            dispose();
        }
    }

    private class MakePaymentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PaymentFrame("Admin");
            dispose();
        }
    }

    private class ViewPaymentsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewPaymentsFrame("Admin");
            dispose();
        }
    }

    private class AttendanceViewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewAttendanceFrame();
            dispose();
        }
    }

    private class ProgressListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ManageProgressFrame();
            dispose();
        }
    }

    private class SendNotificationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SendNotificationFrame("Admin");
            dispose();
        }
    }

    private class CreateSessionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CreateSessionFrame();
            dispose();
        }
    }

    private class AssignSessionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AssignClientSessionFrame();
            dispose();
        }
    }

    private class ViewSessionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ViewSessionsFrame();
            dispose();
        }
    }

    private class GenerateReportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new GenerateReportFrame();
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