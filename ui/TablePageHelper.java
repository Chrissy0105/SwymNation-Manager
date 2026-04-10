package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablePageHelper {

    private TablePageHelper() {
    }

    public static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        UIHelper.styleTable(table);
        return table;
    }

    public static JPanel createTableCard(JTable table, JButton backButton) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(1200, Integer.MAX_VALUE));
        card.setPreferredSize(new Dimension(1000, 520));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230)));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottom.setBackground(Color.WHITE);
        bottom.add(backButton);

        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottom, BorderLayout.SOUTH);

        return card;
    }
}