package ui;

import dao.ClassSessionDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateSessionFrame extends JFrame {

    private JTextField dateField;
    private JTextField timeField;
    private JComboBox<String> levelBox;
    private JTextField capacityField;
    private JButton createButton;
    private JButton backButton;

    public CreateSessionFrame() {
        UIHelper.setupFrame(this, "Create Session");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Create Class Session", UIHelper.PRIMARY_BLUE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(750, 420);

        dateField = new JTextField();
        timeField = new JTextField();
        levelBox = new JComboBox<String>(new String[] { "Beginner", "Developing", "Intermediate", "Advanced" });
        capacityField = new JTextField();

        card.add(UIHelper.createLabeledField("Session Date (YYYY-MM-DD)", dateField));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Time (e.g. 10:00 AM)", timeField));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Level", levelBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Capacity", capacityField));
        card.add(Box.createVerticalStrut(24));

        createButton = UIHelper.createButton("Create Session", UIHelper.PRIMARY_BLUE);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(createButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        createButton.addActionListener(new CreateButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void createSession() {
        String sessionDate = dateField.getText().trim();
        String time = timeField.getText().trim();
        String level = levelBox.getSelectedItem().toString();
        String capacityText = capacityField.getText().trim();

        if (ValidationHelper.isEmpty(sessionDate) ||
                ValidationHelper.isEmpty(time) ||
                ValidationHelper.isEmpty(capacityText)) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (!ValidationHelper.isInteger(capacityText)) {
            JOptionPane.showMessageDialog(this, "Capacity must be a valid number.");
            return;
        }

        int capacity = Integer.parseInt(capacityText);

        if (capacity <= 0) {
            JOptionPane.showMessageDialog(this, "Capacity must be greater than 0.");
            return;
        }

        ClassSessionDAO dao = new ClassSessionDAO();
        boolean success = dao.createSession(sessionDate, time, level, capacity);

        if (success) {
            JOptionPane.showMessageDialog(this, "Session created successfully.");
            dateField.setText("");
            timeField.setText("");
            levelBox.setSelectedIndex(0);
            capacityField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Could not create session.");
        }
    }

    private class CreateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createSession();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AdminDashboardFrame();
            dispose();
        }
    }
}