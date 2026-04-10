package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAttendanceFrame extends JFrame {

    private JTable table;
    private JButton backButton;

    public ViewAttendanceFrame() {
        UIHelper.setupFrame(this, "View Attendance");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Attendance Records", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = TablePageHelper.createTableCard(createAttendanceTable(), createBackButton());
        wrapper.add(card, java.awt.BorderLayout.CENTER);

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private JTable createAttendanceTable() {
        String[] columns = {
                "Attendance ID", "Date", "Status", "Client Name", "Client Email"
        };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = TablePageHelper.createStyledTable(model);

        loadAttendance(model);
        return table;
    }

    private void loadAttendance(DefaultTableModel model) {
        String sql = "SELECT a.id, a.date, a.status, u.name, u.email " +
                "FROM attendance_records a " +
                "JOIN users u ON a.user_id = u.id " +
                "ORDER BY a.id DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getDate("date"),
                        rs.getString("status"),
                        rs.getString("name"),
                        rs.getString("email")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load attendance.");
            e.printStackTrace();
        }
    }

    private JButton createBackButton() {
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);
        backButton.addActionListener(new BackButtonListener());
        return backButton;
    }

    private class BackButtonListener implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            new AdminDashboardFrame();
            dispose();
        }
    }
}