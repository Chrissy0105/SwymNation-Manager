package ui;

import dao.LoginDAO;
import dao.ScheduleNotificationDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleBox;
    private JButton loginButton;
    private JButton backButton;

    public LoginFrame() {
        UIHelper.setupFrame(this, "Swym Nation - Login");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Login", UIHelper.PRIMARY_BLUE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(520, 340);

        emailField = new JTextField();
        passwordField = new JPasswordField();
        roleBox = new JComboBox<String>(new String[] { "Admin", "Receptionist", "Instructor", "Client" });

        card.add(UIHelper.createLabeledField("Email", emailField));
        card.add(Box.createVerticalStrut(16));
        card.add(UIHelper.createLabeledField("Password", passwordField));
        card.add(Box.createVerticalStrut(16));
        card.add(UIHelper.createLabeledField("Role", roleBox));
        card.add(Box.createVerticalStrut(28));

        loginButton = UIHelper.createButton("Login", UIHelper.PRIMARY_BLUE);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(loginButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        loginButton.addActionListener(new LoginButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void loginUser() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String selectedRole = roleBox.getSelectedItem().toString();

        if (ValidationHelper.isEmpty(email) || ValidationHelper.isEmpty(password)) {
            JOptionPane.showMessageDialog(this, "Please enter email and password.");
            return;
        }

        if (!ValidationHelper.isEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        LoginDAO loginDAO = new LoginDAO();
        boolean valid = loginDAO.validateLogin(email, password, selectedRole);

        if (!valid) {
            JOptionPane.showMessageDialog(this, "Invalid login credentials.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Login successful as " + selectedRole + ".");

        if (selectedRole.equals("Admin")) {
            new AdminDashboardFrame();
        } else if (selectedRole.equals("Receptionist")) {
            new ReceptionistDashboardFrame();
        } else if (selectedRole.equals("Instructor")) {
            new InstructorDashboardFrame();
        } else if (selectedRole.equals("Client")) {
            createSmartScheduleReminderForClient(email);
            new ClientDashboardFrame(email);
        }

        dispose();
    }

    private void createSmartScheduleReminderForClient(String email) {
        ScheduleNotificationDAO dao = new ScheduleNotificationDAO();
        dao.createSmartNextClassNotification(email);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loginUser();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new HomeFrame();
            dispose();
        }
    }
}