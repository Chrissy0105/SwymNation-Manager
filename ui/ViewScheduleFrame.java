package ui;

import dao.ScheduleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ViewScheduleFrame extends JFrame {

    private JTable table;
    private JButton backButton;
    private String clientEmail;

    public ViewScheduleFrame(String clientEmail) {
        this.clientEmail = clientEmail;

        UIHelper.setupFrame(this, "Swym Nation - View Schedule");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("My Class Schedule", UIHelper.PURPLE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = createScheduleCard();
        wrapper.add(card, java.awt.BorderLayout.CENTER);

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private JPanel createScheduleCard() {
        JPanel card = new JPanel(new java.awt.BorderLayout());
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        String[] columns = { "Session ID", "Date", "Time", "Level", "Capacity" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = TablePageHelper.createStyledTable(model);

        loadSchedule(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(220, 225, 230)));

        backButton = UIHelper.createButton("Back", UIHelper.GRAY);
        backButton.addActionListener(new BackButtonListener());

        JPanel bottom = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        bottom.setBackground(java.awt.Color.WHITE);
        bottom.add(backButton);

        card.add(scrollPane, java.awt.BorderLayout.CENTER);
        card.add(bottom, java.awt.BorderLayout.SOUTH);

        return card;
    }

    private void loadSchedule(DefaultTableModel model) {
        model.setRowCount(0);

        ScheduleDAO dao = new ScheduleDAO();
        List<String[]> rows = dao.getClientScheduleByEmail(clientEmail);

        for (int i = 0; i < rows.size(); i++) {
            model.addRow(rows.get(i));
        }

        if (rows.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No scheduled classes found for this client.");
        }
    }

    private class BackButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            new ClientDashboardFrame(clientEmail);
            dispose();
        }
    }
}