package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UIHelper {

    public static final Color BACKGROUND = new Color(241, 245, 249);
    public static final Color CARD_BACKGROUND = new Color(255, 255, 255);
    public static final Color PRIMARY_BLUE = new Color(37, 99, 235);
    public static final Color TEAL = new Color(13, 148, 136);
    public static final Color ORANGE = new Color(234, 88, 12);
    public static final Color PURPLE = new Color(126, 34, 206);
    public static final Color GREEN = new Color(22, 163, 74);
    public static final Color GRAY = new Color(71, 85, 105);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Color TEXT = new Color(15, 23, 42);
    public static final Color MUTED_TEXT = new Color(100, 116, 139);

    private UIHelper() {
    }

    public static void setupFrame(JFrame frame, String title) {
        frame.setTitle(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1180, 760);
        frame.setMinimumSize(new Dimension(960, 640));
        frame.setLocationRelativeTo(null);
    }

    public static void finalizeFrame(JFrame frame, JPanel mainPanel) {
        frame.setContentPane(wrapScrollable(mainPanel));
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    public static JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        return panel;
    }

    public static JPanel createWrapperPanel() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(24, 24, 24, 24));
        return wrapper;
    }

    public static JPanel createFormWrapperPanel() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(24, 24, 24, 24));
        return wrapper;
    }

    public static JPanel createTableWrapperPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BACKGROUND);
        wrapper.setBorder(new EmptyBorder(24, 24, 24, 24));
        return wrapper;
    }

    public static JLabel createHeader(String title, Color color) {
        JLabel header = new JLabel(title, SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
        header.setBackground(color);
        header.setBorder(new EmptyBorder(18, 12, 18, 12));
        return header;
    }

    public static JLabel createSubHeader(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setForeground(MUTED_TEXT);
        label.setBorder(new EmptyBorder(8, 10, 12, 10));
        return label;
    }

    public static JPanel createCardPanel(int width) {
        return createCardPanel(width, 450);
    }

    public static JPanel createCardPanel(int width, int height) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(28, 30, 28, 30)));
        card.setPreferredSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);
        return card;
    }

    public static JPanel createGridCardPanel(int rows, int cols, int width, int height) {
        JPanel card = new JPanel(new GridLayout(rows, cols, 18, 18));
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(26, 26, 26, 26)));
        card.setPreferredSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        return card;
    }

    public static JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 6));
        panel.setBackground(CARD_BACKGROUND);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 82));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 42));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel createLabeledArea(String labelText, JComponent area, int maxHeight) {
        JPanel panel = new JPanel(new BorderLayout(5, 6));
        panel.setBackground(CARD_BACKGROUND);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxHeight));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT);

        panel.add(label, BorderLayout.NORTH);
        panel.add(area, BorderLayout.CENTER);
        return panel;
    }

    public static JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 42));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    public static JPanel createTwoButtonPanel(JButton left, JButton right) {
        JPanel panel = new JPanel(new GridLayout(1, 2, 16, 0));
        panel.setBackground(CARD_BACKGROUND);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        panel.add(left);
        panel.add(right);
        return panel;
    }

    public static JPanel createStatCard(String title, String value, Color accent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(18, 18, 18, 18)));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        accentBar.setPreferredSize(new Dimension(200, 6));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(TEXT);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(MUTED_TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(accentBar);
        panel.add(Box.createVerticalStrut(14));
        panel.add(valueLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(titleLabel);

        return panel;
    }

    public static JPanel createSectionPanel(String title, String subtitle, Color accent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                new EmptyBorder(18, 18, 18, 18)));

        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        accentBar.setPreferredSize(new Dimension(200, 6));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(TEXT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(MUTED_TEXT);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(accentBar);
        panel.add(Box.createVerticalStrut(12));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(6));
        panel.add(subtitleLabel);

        return panel;
    }

    public static JButton createDashboardTile(String title, String subtitle, Color color) {
        String html = "<html><div style='text-align:center;'>" +
                "<div style='font-size:15px; font-weight:bold;'>" + title + "</div>" +
                "<div style='font-size:11px; margin-top:4px;'>" + subtitle + "</div>" +
                "</div></html>";

        JButton button = new JButton(html);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    public static void styleTextArea(JTextArea area) {
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBackground(Color.WHITE);
        area.setForeground(TEXT);
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public static JScrollPane createTextScroll(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER, 1, true));
        return scrollPane;
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setBackground(Color.WHITE);
        table.setForeground(TEXT);
        table.setGridColor(BORDER);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(226, 232, 240));
        table.getTableHeader().setForeground(TEXT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public static JScrollPane wrapScrollable(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BACKGROUND);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        return scrollPane;
    }
}