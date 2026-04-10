package dao;

public class PaymentResult {
    private boolean success;
    private int paymentId;
    private int receiptId;
    private double amount;
    private String method;
    private String recipientEmail;

    public PaymentResult(boolean success, int paymentId, int receiptId, double amount, String method,
            String recipientEmail) {
        this.success = success;
        this.paymentId = paymentId;
        this.receiptId = receiptId;
        this.amount = amount;
        this.method = method;
        this.recipientEmail = recipientEmail;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public double getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }
}