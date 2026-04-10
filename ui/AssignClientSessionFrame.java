package ui;

import dao.ClientClassScheduleDAO;
import dao.SessionListDAO;
import dao.UserListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AssignClientSessionFrame extends JFrame {

    private JComboBox<String> clientBox;
    private JComboBox<String> sessionBox;
    private JButton assignButton;
    private JButton backButton;

    public AssignClientSessionFrame() {
        UIHelper.setupFrame(this, "Assign Client To Session");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Assign Client To Session", UIHelper.PRIMARY_BLUE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(850, 360);

        clientBox = new JComboBox<String>();
        sessionBox = new JComboBox<String>();

        loadClients();
        loadSessions();

        card.add(UIHelper.createLabeledField("Select Client", clientBox));
        card.add(Box.createVerticalStrut(16));
        card.add(UIHelper.createLabeledField("Select Session", sessionBox));
        card.add(Box.createVerticalStrut(24));

        assignButton = UIHelper.createButton("Assign Session", UIHelper.PRIMARY_BLUE);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(assignButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        assignButton.addActionListener(new AssignButtonListener());
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

    private void loadSessions() {
        SessionListDAO dao = new SessionListDAO();
        List<String> sessions = dao.getAllSessions();
        SelectionHelper.loadItems(sessionBox, sessions);
    }

    private void assignSession() {
        String selectedClient = (String) clientBox.getSelectedItem();
        String selectedSession = (String) sessionBox.getSelectedItem();

        String email = SelectionHelper.extractEmail(selectedClient);
        int sessionId = SessionSelectionHelper.extractSessionId(selectedSession);

        if (ValidationHelper.isEmpty(email) || sessionId == -1) {
            JOptionPane.showMessageDialog(this, "Please select both a client and a session.");
            return;
        }

        ClientClassScheduleDAO dao = new ClientClassScheduleDAO();
        boolean success = dao.assignClientToSession(email, sessionId);

        if (success) {
            JOptionPane.showMessageDialog(this, "Client assigned to session successfully.");
            if (clientBox.getItemCount() > 0) {
                clientBox.setSelectedIndex(0);
            }
            if (sessionBox.getItemCount() > 0) {
                sessionBox.setSelectedIndex(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Could not assign client to session. It may already exist.");
        }
    }

    private class AssignButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assignSession();
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