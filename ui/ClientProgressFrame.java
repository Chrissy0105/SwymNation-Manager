package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientProgressFrame extends JFrame {
    private JTextArea progressArea;
    private JButton backButton;
    private String clientEmail;

    public ClientProgressFrame(String clientEmail) {
        this.clientEmail = clientEmail;

        UIHelper.setupFrame(this, "Swym Nation - View Progress");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("My Progress", UIHelper.PURPLE);
        JPanel wrapper = UIHelper.createTableWrapperPanel();

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new javax.swing.border.EmptyBorder(25, 25, 25, 25));
        card.setMaximumSize(new Dimension(1000, Integer.MAX_VALUE));
        card.setPreferredSize(new Dimension(800, 420));

        progressArea = new JTextArea();
        progressArea.setEditable(false);
        progressArea.setFont(new Font("Arial", Font.PLAIN, 18));
        progressArea.setBackground(Color.WHITE);
        progressArea.setText(
                "Performance Level: Developing\n\n" +
                        "Comments:\n\n" +
                        "- Good improvement in breathing control\n" +
                        "- Needs more confidence in deep water\n" +
                        "- Keep practicing kicking technique");

        JScrollPane scrollPane = UIHelper.createTextScroll(progressArea);

        backButton = UIHelper.createButton("Back", UIHelper.GRAY);
        backButton.addActionListener(new BackButtonListener());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(backButton);

        card.add(scrollPane, BorderLayout.CENTER);
        card.add(bottomPanel, BorderLayout.SOUTH);

        wrapper.add(card, BorderLayout.CENTER);

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ClientDashboardFrame(clientEmail);
            dispose();
        }
    }
}