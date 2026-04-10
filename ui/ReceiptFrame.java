package ui;

import dao.PaymentResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

public class ReceiptFrame extends JFrame {

    private JTextArea receiptArea;
    private JButton printButton;
    private JButton closeButton;

    public ReceiptFrame(PaymentResult result) {
        UIHelper.setupFrame(this, "Receipt");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("Payment Receipt", UIHelper.GREEN);
        JPanel wrapper = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(700, 500);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        receiptArea.setText(
                "Receipt ID: " + result.getReceiptId() + "\n\n" +
                        "Payment ID: " + result.getPaymentId() + "\n" +
                        "Recipient: " + result.getRecipientEmail() + "\n" +
                        "Amount: $" + result.getAmount() + "\n" +
                        "Method: " + result.getMethod() + "\n" +
                        "Status: Completed\n");

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        printButton = UIHelper.createButton("Print Receipt", UIHelper.GREEN);
        closeButton = UIHelper.createButton("Close", UIHelper.GRAY);

        JPanel buttonPanel = UIHelper.createTwoButtonPanel(printButton, closeButton);

        card.setLayout(new BorderLayout(15, 15));
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(card);
        wrapper.add(Box.createVerticalGlue());

        printButton.addActionListener(new PrintButtonListener());
        closeButton.addActionListener(new CloseButtonListener());

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private class PrintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                receiptArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(ReceiptFrame.this, "Could not print receipt.");
            }
        }
    }

    private class CloseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}