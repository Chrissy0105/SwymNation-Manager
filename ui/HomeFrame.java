package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {

    private JButton loginButton;
    private JButton exitButton;

    public HomeFrame() {
        UIHelper.setupFrame(this, "Swym Nation Management System");

        JPanel mainPanel = UIHelper.createMainPanel();

        JLabel header = UIHelper.createHeader("Swym Nation Management System", UIHelper.PRIMARY_BLUE);

        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new BoxLayout(bodyPanel, BoxLayout.Y_AXIS));
        bodyPanel.setBackground(UIHelper.BACKGROUND);
        bodyPanel.setBorder(new javax.swing.border.EmptyBorder(24, 24, 24, 24));

        JPanel heroCard = UIHelper.createCardPanel(980, 280);
        heroCard.setLayout(new BorderLayout(20, 20));

        JPanel heroText = new JPanel();
        heroText.setLayout(new BoxLayout(heroText, BoxLayout.Y_AXIS));
        heroText.setBackground(UIHelper.CARD_BACKGROUND);

        JLabel title = new JLabel("Manage your swim studio with confidence");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(UIHelper.TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "<html><div style='width:420px;'>Register users, create sessions, assign schedules, process payments, send notifications, and generate reports from one place.</div></html>");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(UIHelper.MUTED_TEXT);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton = UIHelper.createButton("Login", UIHelper.PRIMARY_BLUE);
        exitButton = UIHelper.createButton("Exit", UIHelper.GRAY);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        buttonPanel.setBackground(UIHelper.CARD_BACKGROUND);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        heroText.add(title);
        heroText.add(Box.createVerticalStrut(14));
        heroText.add(subtitle);
        heroText.add(Box.createVerticalStrut(22));
        heroText.add(buttonPanel);

        JPanel statsPanel = new JPanel(new GridLayout(3, 1, 0, 12));
        statsPanel.setBackground(UIHelper.CARD_BACKGROUND);
        statsPanel.add(UIHelper.createStatCard("User Roles", "4", UIHelper.PRIMARY_BLUE));
        statsPanel.add(UIHelper.createStatCard("Core Modules", "8+", UIHelper.GREEN));
        statsPanel.add(UIHelper.createStatCard("Reports", "Live DB", UIHelper.PURPLE));

        heroCard.add(heroText, BorderLayout.CENTER);
        heroCard.add(statsPanel, BorderLayout.EAST);

        JPanel featureCard = UIHelper.createCardPanel(980, 220);
        featureCard.setLayout(new GridLayout(1, 3, 16, 16));
        featureCard.add(UIHelper.createStatCard("Registration", "Admin & Reception", UIHelper.TEAL));
        featureCard.add(UIHelper.createStatCard("Scheduling", "Sessions & Assignments", UIHelper.ORANGE));
        featureCard.add(UIHelper.createStatCard("Notifications", "Payments & Reminders", UIHelper.GREEN));

        bodyPanel.add(heroCard);
        bodyPanel.add(Box.createVerticalStrut(18));
        bodyPanel.add(featureCard);

        loginButton.addActionListener(new LoginButtonListener());
        exitButton.addActionListener(new ExitButtonListener());

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginFrame();
            dispose();
        }
    }

    private class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}