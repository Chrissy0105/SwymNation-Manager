package ui;

import dao.PaymentDAO;
import dao.PaymentResult;
import dao.UserListDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PaymentFrame extends JFrame {
    private JComboBox<String> recipientBox;
    private JTextField amountField;
    private JComboBox<String> methodBox;
    private JTextField cardNameField;
    private JTextField cardNumberField;
    private JTextField cvvField;
    private JTextField expiryDateField;
    private JPanel cardDetailsPanel;
    private JButton payButton;
    private JButton backButton;

    private String role;

    public PaymentFrame(String role) {
        this.role = role;

        UIHelper.setupFrame(this, "Swym Nation - Payment");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader(getHeaderTitle(), UIHelper.PRIMARY_BLUE);
        JPanel wrapperPanel = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(850, 760);

        recipientBox = new JComboBox<String>();
        loadRecipients();

        amountField = new JTextField();
        methodBox = new JComboBox<String>(new String[] { "Cash", "Debit Card", "Credit Card", "Bank Transfer" });

        cardNameField = new JTextField();
        cardNumberField = new JTextField();
        cvvField = new JTextField();
        expiryDateField = new JTextField();

        card.add(UIHelper.createLabeledField(getRecipientLabel(), recipientBox));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Amount", amountField));
        card.add(Box.createVerticalStrut(14));
        card.add(UIHelper.createLabeledField("Payment Method", methodBox));
        card.add(Box.createVerticalStrut(14));

        cardDetailsPanel = new JPanel();
        cardDetailsPanel.setLayout(new BoxLayout(cardDetailsPanel, BoxLayout.Y_AXIS));
        cardDetailsPanel.setBackground(java.awt.Color.WHITE);

        cardDetailsPanel.add(UIHelper.createLabeledField("Card Name", cardNameField));
        cardDetailsPanel.add(Box.createVerticalStrut(12));
        cardDetailsPanel.add(UIHelper.createLabeledField("Card Number", cardNumberField));
        cardDetailsPanel.add(Box.createVerticalStrut(12));
        cardDetailsPanel.add(UIHelper.createLabeledField("CVV", cvvField));
        cardDetailsPanel.add(Box.createVerticalStrut(12));
        cardDetailsPanel.add(UIHelper.createLabeledField("Expiry Date (MM/YY)", expiryDateField));

        card.add(cardDetailsPanel);
        card.add(Box.createVerticalStrut(24));

        payButton = UIHelper.createButton(getButtonText(), UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        card.add(UIHelper.createTwoButtonPanel(payButton, backButton));

        wrapperPanel.add(Box.createVerticalStrut(20));
        wrapperPanel.add(card);
        wrapperPanel.add(Box.createVerticalGlue());

        methodBox.addActionListener(new PaymentMethodListener());
        payButton.addActionListener(new PayButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, java.awt.BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, java.awt.BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
        updateCardFieldsVisibility();
    }

    private void loadRecipients() {
        UserListDAO dao = new UserListDAO();
        List<String> items;

        if ("Admin".equalsIgnoreCase(role)) {
            items = dao.getAllUsers();
        } else {
            items = dao.getUsersByRole("Client");
        }

        SelectionHelper.loadItems(recipientBox, items);
    }

    private String getHeaderTitle() {
        if ("Client".equalsIgnoreCase(role)) {
            return "Make Payment";
        }
        return "Process Payment";
    }

    private String getButtonText() {
        if ("Client".equalsIgnoreCase(role)) {
            return "Make Payment";
        }
        return "Process Payment";
    }

    private String getRecipientLabel() {
        if ("Admin".equalsIgnoreCase(role)) {
            return "Select Recipient";
        }
        return "Select Client";
    }

    private void updateCardFieldsVisibility() {
        String method = methodBox.getSelectedItem().toString();
        boolean showCardFields = method.equals("Debit Card") || method.equals("Credit Card");
        cardDetailsPanel.setVisible(showCardFields);
        revalidate();
        repaint();
    }

    private boolean validateCardFields() {
        String cardName = cardNameField.getText().trim();
        String cardNumber = cardNumberField.getText().trim();
        String cvv = cvvField.getText().trim();
        String expiryDate = expiryDateField.getText().trim();

        if (ValidationHelper.isEmpty(cardName) ||
                ValidationHelper.isEmpty(cardNumber) ||
                ValidationHelper.isEmpty(cvv) ||
                ValidationHelper.isEmpty(expiryDate)) {
            JOptionPane.showMessageDialog(this, "Please fill in all card details.");
            return false;
        }

        if (!ValidationHelper.isLettersAndSpaces(cardName)) {
            JOptionPane.showMessageDialog(this, "Card name must contain only letters and spaces.");
            return false;
        }

        if (!ValidationHelper.isCardNumber(cardNumber)) {
            JOptionPane.showMessageDialog(this, "Card number must be 13 to 19 digits.");
            return false;
        }

        if (!ValidationHelper.isCVV(cvv)) {
            JOptionPane.showMessageDialog(this, "CVV must be 3 or 4 digits.");
            return false;
        }

        if (!ValidationHelper.isExpiryDate(expiryDate)) {
            JOptionPane.showMessageDialog(this, "Expiry date must be in MM/YY format.");
            return false;
        }

        return true;
    }

    private void processPayment() {
        String selectedRecipient = (String) recipientBox.getSelectedItem();
        String email = SelectionHelper.extractEmail(selectedRecipient);
        String amountText = amountField.getText().trim();
        String method = methodBox.getSelectedItem().toString();

        if (ValidationHelper.isEmpty(email) || ValidationHelper.isEmpty(amountText)) {
            JOptionPane.showMessageDialog(this, "Please select a recipient and enter the amount.");
            return;
        }

        if (!ValidationHelper.isDouble(amountText)) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.");
            return;
        }

        double amount = Double.parseDouble(amountText);

        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Amount must be greater than 0.");
            return;
        }

        if (method.equals("Debit Card") || method.equals("Credit Card")) {
            if (!validateCardFields()) {
                return;
            }
        }

        PaymentDAO dao = new PaymentDAO();
        PaymentResult result = dao.makePayment(email, amount, method);

        if (result.isSuccess()) {
            JOptionPane.showMessageDialog(this, "Payment saved successfully.");
            clearFields();
            new ReceiptFrame(result);
        } else {
            JOptionPane.showMessageDialog(this, "Payment failed. Check that the selected user exists.");
        }
    }

    private void clearFields() {
        if (recipientBox.getItemCount() > 0) {
            recipientBox.setSelectedIndex(0);
        }
        amountField.setText("");
        methodBox.setSelectedIndex(0);
        cardNameField.setText("");
        cardNumberField.setText("");
        cvvField.setText("");
        expiryDateField.setText("");
        updateCardFieldsVisibility();
    }

    private void goBack() {
        if ("Admin".equals(role)) {
            new AdminDashboardFrame();
        } else if ("Receptionist".equals(role)) {
            new ReceptionistDashboardFrame();
        } else if ("Client".equals(role)) {
            String selectedRecipient = (String) recipientBox.getSelectedItem();
            String email = SelectionHelper.extractEmail(selectedRecipient);
            new ClientDashboardFrame(email);
        } else {
            new HomeFrame();
        }
        dispose();
    }

    private class PaymentMethodListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateCardFieldsVisibility();
        }
    }

    private class PayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            processPayment();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            goBack();
        }
    }
}