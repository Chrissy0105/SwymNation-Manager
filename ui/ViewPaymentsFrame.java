package ui;

import dao.PaymentResult;
import dao.ReceiptDAO;
import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewPaymentsFrame extends JFrame {

    private JTable table;
    private JButton backButton;
    private JButton receiptButton;
    private String role;

    public ViewPaymentsFrame(String role) {
        this.role = role;

        UIHelper.setupFrame(this, "View Payments");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Payments", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = createPaymentsCard();
        wrapper.add(card, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private JPanel createPaymentsCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        String[] columns = {
                "Payment ID", "Name", "Email", "Role", "Amount", "Status", "Created At", "Method"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = TablePageHelper.createStyledTable(model);

        loadPayments(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        receiptButton = UIHelper.createButton("View Receipt", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        receiptButton.addActionListener(new ViewReceiptListener());
        backButton.addActionListener(new BackButtonListener(role));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(Color.WHITE);
        bottom.add(receiptButton);
        bottom.add(backButton);

        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }

    private void loadPayments(DefaultTableModel model) {
        String sql = "SELECT p.id, u.name, u.email, u.role, p.amount, p.status, p.created_at, p.payment_method " +
                "FROM payments p " +
                "JOIN users u ON p.user_id = u.id " +
                "ORDER BY p.id DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getDouble("amount"),
                        rs.getString("status"),
                        rs.getTimestamp("created_at"),
                        rs.getString("payment_method")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load payments.");
            e.printStackTrace();
        }
    }

    private class ViewReceiptListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(ViewPaymentsFrame.this, "Please select a payment first.");
                return;
            }

            int paymentId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

            ReceiptDAO dao = new ReceiptDAO();
            PaymentResult result = dao.getReceiptByPaymentId(paymentId);

            if (result.isSuccess()) {
                new ReceiptFrame(result);
            } else {
                JOptionPane.showMessageDialog(ViewPaymentsFrame.this, "Receipt not found for that payment.");
            }
        }
    }

    private class BackButtonListener implements java.awt.event.ActionListener {
        private String role;

        public BackButtonListener(String role) {
            this.role = role;
        }

        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if ("Admin".equals(role)) {
                new AdminDashboardFrame();
            } else {
                new ReceptionistDashboardFrame();
            }
            dispose();
        }
    }
}