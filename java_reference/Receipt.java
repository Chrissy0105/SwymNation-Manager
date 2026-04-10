package java_reference;
import java.util.Date;

public class Receipt {
    private int receiptId;
    private Date issueDate;
    private Payment payment;

    public Receipt(int receiptId, Date issueDate, Payment payment) {
        this.receiptId = receiptId;
        this.issueDate = issueDate;
        this.payment = payment;
    }

    public String generateReceipt() {
        if (payment == null) {
            return "Receipt ID: " + receiptId + ", Issue Date: " + issueDate;
        }

        return "Receipt ID: " + receiptId +
                ", Issue Date: " + issueDate +
                ", Payment Amount: $" + payment.getAmount() +
                ", Status: " + payment.getStatus();
    }

    public void printReceipt() {
        System.out.println(generateReceipt());
    }

    public int getReceiptId() {
        return receiptId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public Payment getPayment() {
        return payment;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId=" + receiptId +
                ", issueDate=" + issueDate +
                ", payment=" + payment +
                '}';
    }
}