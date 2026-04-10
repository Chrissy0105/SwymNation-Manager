package ui;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ManageProgressFrame extends JFrame {

    private JTable table;
    private JButton backButton;

    public ManageProgressFrame() {
        UIHelper.setupFrame(this, "Manage Progress");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Manage Progress Records", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        DefaultTableModel model = new DefaultTableModel();
        table = TablePageHelper.createStyledTable(model);

        loadProgress(model);

        backButton = UIHelper.createButton("Back", UIHelper.GRAY);
        backButton.addActionListener(new BackButtonListener());

        JPanel card = TablePageHelper.createTableCard(table, backButton);
        wrapper.add(card, java.awt.BorderLayout.CENTER);

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapper, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void loadProgress(DefaultTableModel model) {
        String sql = "SELECT * FROM student_progress";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }

                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load progress records.");
            e.printStackTrace();
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