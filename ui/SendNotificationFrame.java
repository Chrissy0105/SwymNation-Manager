package ui;

import dao.NotificationDAO;
import dao.UserListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SendNotificationFrame extends JFrame {

    private JComboBox<String> clientBox;
    private JTextArea messageArea;
    private JButton sendButton;
    private JButton backButton;
    private String role;

    public SendNotificationFrame(String role) {
        this.role = role;

        UIHelper.setupFrame(this, "Send Notification");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Send Notification", UIHelper.GREEN);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(850, 560);

        clientBox = new JComboBox<String>();
        loadClients();

        messageArea = new JTextArea(8, 20);
        UIHelper.styleTextArea(messageArea);

        JScrollPane messageScroll = UIHelper.createTextScroll(messageArea);
        messageScroll.setPreferredSize(new java.awt.Dimension(200, 200));
        messageScroll.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 220));

        card.add(UIHelper.createLabeledField("Select Client", clientBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledArea("Message", messageScroll, 260));
        card.add(Box.createVerticalStrut(24));

        sendButton = UIHelper.createButton("Send Notification", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(sendButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        sendButton.addActionListener(new SendButtonListener());
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

    private void sendNotification() {
        String selectedClient = (String) clientBox.getSelectedItem();
        String email = SelectionHelper.extractEmail(selectedClient);
        String message = messageArea.getText().trim();

        if (ValidationHelper.isEmpty(email) || ValidationHelper.isEmpty(message)) {
            JOptionPane.showMessageDialog(this, "Please select a client and enter a message.");
            return;
        }

        NotificationDAO dao = new NotificationDAO();
        boolean success = dao.sendManualNotification(email, message);

        if (success) {
            JOptionPane.showMessageDialog(this, "Notification sent successfully.");
            if (clientBox.getItemCount() > 0) {
                clientBox.setSelectedIndex(0);
            }
            messageArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Could not send notification.");
        }
    }

    private void goBack() {
        if ("Admin".equals(role)) {
            new AdminDashboardFrame();
        } else if ("Receptionist".equals(role)) {
            new ReceptionistDashboardFrame();
        } else if ("Instructor".equals(role)) {
            new InstructorDashboardFrame();
        } else {
            new HomeFrame();
        }
        dispose();
    }

    private class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sendNotification();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            goBack();
        }
    }
}