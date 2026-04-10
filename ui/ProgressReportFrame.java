package ui;

import dao.ProgressReportDAO;
import dao.UserListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProgressReportFrame extends JFrame {
    private JComboBox<String> clientBox;
    private JComboBox<String> levelBox;
    private JTextArea commentsArea;
    private JButton saveButton;
    private JButton backButton;

    public ProgressReportFrame() {
        UIHelper.setupFrame(this, "Swym Nation - Progress Report");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Update Progress Report", UIHelper.ORANGE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(850, 620);

        clientBox = new JComboBox<String>();
        loadClients();

        levelBox = new JComboBox<String>(new String[] { "Beginner", "Developing", "Intermediate", "Advanced" });
        commentsArea = new JTextArea(8, 20);
        UIHelper.styleTextArea(commentsArea);

        JScrollPane commentsScroll = UIHelper.createTextScroll(commentsArea);
        commentsScroll.setPreferredSize(new java.awt.Dimension(200, 180));
        commentsScroll.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 200));

        card.add(UIHelper.createLabeledField("Select Client", clientBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Performance Level", levelBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledArea("Comments", commentsScroll, 240));
        card.add(Box.createVerticalStrut(24));

        saveButton = UIHelper.createButton("Save Report", UIHelper.ORANGE);
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

    private void saveReport() {
        String selectedClient = (String) clientBox.getSelectedItem();
        String email = SelectionHelper.extractEmail(selectedClient);
        String level = levelBox.getSelectedItem().toString();
        String comments = commentsArea.getText().trim();

        if (ValidationHelper.isEmpty(email) || ValidationHelper.isEmpty(comments)) {
            JOptionPane.showMessageDialog(this, "Please select a client and enter comments.");
            return;
        }

        ProgressReportDAO dao = new ProgressReportDAO();
        boolean success = dao.saveProgressReport(email, level, comments);

        if (success) {
            JOptionPane.showMessageDialog(this, "Progress report saved successfully.");
            if (clientBox.getItemCount() > 0) {
                clientBox.setSelectedIndex(0);
            }
            levelBox.setSelectedIndex(0);
            commentsArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Progress report could not be saved.");
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveReport();
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