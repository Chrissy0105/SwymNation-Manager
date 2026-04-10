package ui;

import dao.ClientDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterClientFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField stageField;
    private JButton registerButton;
    private JButton backButton;

    private String role;

    public RegisterClientFrame(String role) {
        this.role = role;

        UIHelper.setupFrame(this, "Swym Nation - Register Client");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Register Client", UIHelper.PRIMARY_BLUE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel formCard = UIHelper.createCardPanel(850, 700);

        nameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        ageField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        stageField = new JTextField();

        formCard.add(UIHelper.createLabeledField("Full Name", nameField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Email", emailField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Password", passwordField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Age", ageField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Phone", phoneField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Address", addressField));
        formCard.add(Box.createVerticalStrut(12));
        formCard.add(UIHelper.createLabeledField("Current Stage", stageField));
        formCard.add(Box.createVerticalStrut(22));

        registerButton = UIHelper.createButton("Register Client", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        formCard.add(UIHelper.createTwoButtonPanel(registerButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(formCard);
        wrapperPanel.add(Box.createVerticalGlue());

        registerButton.addActionListener(new RegisterButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void registerClient() {
        String fullName = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String ageText = ageField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String currentStage = stageField.getText().trim();

        if (ValidationHelper.isEmpty(fullName) || ValidationHelper.isEmpty(email)
                || ValidationHelper.isEmpty(password) || ValidationHelper.isEmpty(ageText)) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        if (!ValidationHelper.isEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return;
        }

        if (!ValidationHelper.isInteger(ageText)) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
            return;
        }

        int age = Integer.parseInt(ageText);

        ClientDAO clientDAO = new ClientDAO();
        boolean success = clientDAO.registerClient(fullName, email, password, age, phone, address, currentStage);

        if (success) {
            JOptionPane.showMessageDialog(this, "Client registered successfully.");
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Email may already exist.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        ageField.setText("");
        phoneField.setText("");
        addressField.setText("");
        stageField.setText("");
    }

    private void goBack() {
        if ("Admin".equals(role)) {
            new AdminDashboardFrame();
        } else if ("Receptionist".equals(role)) {
            new ReceptionistDashboardFrame();
        } else {
            new HomeFrame();
        }
        dispose();
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            registerClient();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            goBack();
        }
    }
}