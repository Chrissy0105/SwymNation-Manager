package ui;

import dao.SessionManagementDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewSessionsFrame extends JFrame {

    private JTable table;
    private JButton deleteButton;
    private JButton backButton;

    public ViewSessionsFrame() {
        UIHelper.setupFrame(this, "View Sessions");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("All Class Sessions", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = createSessionsCard();
        wrapper.add(card, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private JPanel createSessionsCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        String[] columns = { "Session ID", "Date", "Time", "Level", "Capacity" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = TablePageHelper.createStyledTable(model);

        loadSessions(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        deleteButton = UIHelper.createButton("Delete Session", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        deleteButton.addActionListener(new DeleteButtonListener());
        backButton.addActionListener(new BackButtonListener());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(Color.WHITE);
        bottom.add(deleteButton);
        bottom.add(backButton);

        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    private void loadSessions(DefaultTableModel model) {
        model.setRowCount(0);

        SessionManagementDAO dao = new SessionManagementDAO();
        List<String[]> sessions = dao.getAllSessions();

        for (int i = 0; i < sessions.size(); i++) {
            model.addRow(sessions.get(i));
        }
    }

    private class DeleteButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ViewSessionsFrame.this, "Please select a session.");
                return;
            }

            int sessionId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

            int confirm = JOptionPane.showConfirmDialog(
                    ViewSessionsFrame.this,
                    "Are you sure you want to delete session ID " + sessionId + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            SessionManagementDAO dao = new SessionManagementDAO();
            boolean success = dao.deleteSession(sessionId);

            if (success) {
                JOptionPane.showMessageDialog(ViewSessionsFrame.this, "Session deleted successfully.");
                loadSessions((DefaultTableModel) table.getModel());
            } else {
                JOptionPane.showMessageDialog(
                        ViewSessionsFrame.this,
                        "Could not delete session. It may be linked to schedules or attendance.");
            }
        }
    }

    private class BackButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            new AdminDashboardFrame();
            dispose();
        }
    }
}