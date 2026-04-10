package ui;

import dao.NotificationDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class NotificationsFrame extends JFrame {

    private JTable table;
    private JButton backButton;
    private JButton markReadButton;
    private String clientEmail;

    public NotificationsFrame(String clientEmail) {
        this.clientEmail = clientEmail;

        UIHelper.setupFrame(this, "My Notifications");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("My Notifications", UIHelper.PURPLE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = createNotificationsCard();
        wrapper.add(card, java.awt.BorderLayout.CENTER);

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private JPanel createNotificationsCard() {
        JPanel card = new JPanel(new java.awt.BorderLayout());
        card.setBackground(java.awt.Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        String[] columns = { "ID", "Message", "Type", "Read" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = TablePageHelper.createStyledTable(model);

        loadNotifications(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(220, 225, 230)));

        markReadButton = UIHelper.createButton("Mark As Read", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        markReadButton.addActionListener(new MarkReadListener());
        backButton.addActionListener(new BackListener());

        JPanel bottom = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));
        bottom.setBackground(java.awt.Color.WHITE);
        bottom.add(markReadButton);
        bottom.add(backButton);

        card.add(scrollPane, java.awt.BorderLayout.CENTER);
        card.add(bottom, java.awt.BorderLayout.SOUTH);

        return card;
    }

    private void loadNotifications(DefaultTableModel model) {
        model.setRowCount(0);

        NotificationDAO dao = new NotificationDAO();
        List<String[]> notifications = dao.getNotificationsByEmail(clientEmail);

        for (int i = 0; i < notifications.size(); i++) {
            model.addRow(notifications.get(i));
        }
    }

    private class MarkReadListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(NotificationsFrame.this, "Please select a notification.");
                return;
            }

            int notificationId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

            NotificationDAO dao = new NotificationDAO();
            boolean success = dao.markAsRead(notificationId);

            if (success) {
                JOptionPane.showMessageDialog(NotificationsFrame.this, "Notification marked as read.");
                loadNotifications((DefaultTableModel) table.getModel());
            } else {
                JOptionPane.showMessageDialog(NotificationsFrame.this, "Could not update notification.");
            }
        }
    }

    private class BackListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            new ClientDashboardFrame(clientEmail);
            dispose();
        }
    }
}