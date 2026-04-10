package ui;

import dao.UserRegistrationDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterInstructorFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton backButton;

    public RegisterInstructorFrame() {
        UIHelper.setupFrame(this, "Register Instructor");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Register Instructor", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(700, 360);

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();

        card.add(UIHelper.createLabeledField("Full Name", nameField));
        card.add(Box.createVerticalStrut(16));
        card.add(UIHelper.createLabeledField("Email", emailField));
        card.add(Box.createVerticalStrut(16));
        card.add(UIHelper.createLabeledField("Password", passwordField));
        card.add(Box.createVerticalStrut(28));

        registerButton = UIHelper.createButton("Register Instructor", UIHelper.PRIMARY_BLUE);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(registerButton, backButton));

        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(card);
        wrapper.add(Box.createVerticalGlue());

        registerButton.addActionListener(new RegisterListener());
        backButton.addActionListener(new BackListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void registerInstructor() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (ValidationHelper.isEmpty(name) || ValidationHelper.isEmpty(email) || ValidationHelper.isEmpty(password)) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!ValidationHelper.isEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        UserRegistrationDAO dao = new UserRegistrationDAO();
        boolean success = dao.registerUser(name, email, password, "Instructor");

        if (success) {
            JOptionPane.showMessageDialog(this, "Instructor registered successfully.");
            nameField.setText("");
            emailField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may already exist.");
        }
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            registerInstructor();
        }
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AdminDashboardFrame();
            dispose();
        }
    }
}