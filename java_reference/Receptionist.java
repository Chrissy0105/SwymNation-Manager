package java_reference;
import java.util.Date;

public class Receptionist extends User {
    private int employeeId;

    public Receptionist(int userId, String name, String email, String password, String role, int employeeId) {
        super(userId, name, email, password, role);
        this.employeeId = employeeId;
    }

    public boolean registerClient(Client client) {
        if (client == null) {
            return false;
        }

        System.out.println("Receptionist " + getName() +
                " registered client: " + client.getName());
        return true;
    }

    public Payment processPayment(Client client, double amount) {
        if (client == null || amount <= 0) {
            System.out.println("Invalid client or amount.");
            return null;
        }

        Payment payment = new Payment(amount, new Date());
        if (payment.processPayment()) {
            System.out.println("Payment processed for client: " + client.getName());
            return payment;
        }

        System.out.println("Payment failed for client: " + client.getName());
        return null;
    }

    public Receipt generateReceipt(Payment payment) {
        if (payment == null || !payment.verifyPayment()) {
            System.out.println("Cannot generate receipt. Payment not verified.");
            return null;
        }

        Receipt receipt = new Receipt(payment.getPaymentId(), new Date(), payment);
        System.out.println("Receipt generated for payment ID: " + payment.getPaymentId());
        return receipt;
    }

    public void sendNotification(Client client, String message) {
        if (client == null || message == null || message.trim().isEmpty()) {
            System.out.println("Invalid notification details.");
            return;
        }

        client.receiveNotification(message);
    }

    @Override
    public void logout() {
        super.logout();
        System.out.println("Receptionist-specific logout actions completed for: " + getName());
    }

    public int getEmployeeId() {
        return employeeId;
    }

    @Override
    public String toString() {
        return "Receptionist{" +
                "employeeId=" + employeeId +
                ", userId=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}