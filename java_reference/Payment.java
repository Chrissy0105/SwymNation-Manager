package java_reference;
import java.util.Date;

public class Payment {
    private static int idCounter = 1;

    private int paymentId;
    private double amount;
    private Date date;
    private String status;

    public Payment(double amount, Date date) {
        this.paymentId = idCounter++;
        this.amount = amount;
        this.date = date;
        this.status = "Pending";
    }

    public boolean processPayment() {
        if (amount <= 0) {
            status = "Failed";
            return false;
        }

        status = "Completed";
        return true;
    }

    public boolean verifyPayment() {
        return "Completed".equalsIgnoreCase(status);
    }

    public boolean refundPayment() {
        if ("Completed".equalsIgnoreCase(status)) {
            status = "Refunded";
            return true;
        }
        return false;
    }

    public String getPaymentDetails() {
        return "Payment ID: " + paymentId +
                ", Amount: $" + amount +
                ", Date: " + date +
                ", Status: " + status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", amount=" + amount +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}