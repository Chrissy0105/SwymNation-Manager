package ui;

import dao.AttendanceDAO;
import dao.UserListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AttendanceFrame extends JFrame {
    private JComboBox<String> clientBox;
    private JComboBox<String> statusBox;
    private JButton saveButton;
    private JButton backButton;

    public AttendanceFrame() {
        UIHelper.setupFrame(this, "Swym Nation - Attendance");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Take Attendance", UIHelper.ORANGE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(700, 280);

        clientBox = new JComboBox<String>();
        loadClients();

        statusBox = new JComboBox<String>(new String[] { "Present", "Absent", "Pending" });

        card.add(UIHelper.createLabeledField("Select Client", clientBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Status", statusBox));
        card.add(Box.createVerticalStrut(24));

        saveButton = UIHelper.createButton("Save Attendance", UIHelper.ORANGE);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(saveButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        saveButton.addActionListener(new SaveButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void loadClients() {
        UserListDAO dao = new UserListDAO();
        List<String> clients = dao.getUsersByRole("Client");
        SelectionHelper.loadItems(clientBox, clients);
    }

    private void saveAttendance() {
        String selectedClient = (String) clientBox.getSelectedItem();
        String email = SelectionHelper.extractEmail(selectedClient);
        String status = statusBox.getSelectedItem().toString();

        if (ValidationHelper.isEmpty(email)) {
            JOptionPane.showMessageDialog(this, "Please select a client.");
            return;
        }

        AttendanceDAO dao = new AttendanceDAO();
        boolean success = dao.saveAttendance(email, status);

        if (success) {
            JOptionPane.showMessageDialog(this, "Attendance saved successfully.");
            if (clientBox.getItemCount() > 0) {
                clientBox.setSelectedIndex(0);
            }
            statusBox.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Attendance could not be saved.");
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAttendance();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new InstructorDashboardFrame();
            dispose();
        }
    }
}