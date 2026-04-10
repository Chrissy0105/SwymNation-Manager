package java_reference;

public class Client extends User {
    private int clientId;
    private String subscriptionStatus;

    public Client(int userId, String name, String email, String password, String role,
            int clientId, String subscriptionStatus) {
        super(userId, name, email, password, role);
        this.clientId = clientId;
        this.subscriptionStatus = subscriptionStatus;
    }

    public void viewSchedule() {
        System.out.println("Viewing class schedule for client: " + getName() +
                " (ID: " + clientId + ")");
    }

    public void viewProgress() {
        System.out.println("Viewing progress for client: " + getName() +
                " (ID: " + clientId + ")");
    }

    public boolean updateSubscriptionStatus(String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            return false;
        }
        this.subscriptionStatus = newStatus;
        return true;
    }

    public boolean cancelSubscription() {
        if ("active".equalsIgnoreCase(subscriptionStatus)) {
            this.subscriptionStatus = "canceled";
            return true;
        }
        return false;
    }

    public boolean reactivateSubscription() {
        if ("canceled".equalsIgnoreCase(subscriptionStatus)) {
            this.subscriptionStatus = "active";
            return true;
        }
        return false;
    }

    public boolean isSubscriptionActive() {
        return "active".equalsIgnoreCase(subscriptionStatus);
    }

    public boolean makePayment(double amount) {
        if (amount <= 0) {
            return false;
        }
        System.out.println("Processing payment of $" + amount + " for client: " + getName());
        return true;
    }

    public void receiveNotification(String message) {
        System.out.println("Notification for " + getName() + ": " + message);
    }

    public boolean joinSession(ClassSession session) {
        if (session == null) {
            return false;
        }

        if (!isSubscriptionActive()) {
            System.out.println(getName() + " cannot join session because subscription is not active.");
            return false;
        }

        if (session.enrollStudent()) {
            System.out.println(getName() + " successfully joined session ID: " + session.getSessionId());
            return true;
        }

        System.out.println("Session ID " + session.getSessionId() + " is full.");
        return false;
    }

    public boolean leaveSession(ClassSession session) {
        if (session == null) {
            return false;
        }

        if (session.dropStudent()) {
            System.out.println(getName() + " left session ID: " + session.getSessionId());
            return true;
        }

        System.out.println("No students are enrolled in session ID: " + session.getSessionId());
        return false;
    }

    @Override
    public void logout() {
        super.logout();
        System.out.println("Client-specific logout actions completed for: " + getName());
    }

    public int getClientId() {
        return clientId;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", subscriptionStatus='" + subscriptionStatus + '\'' +
                ", userId=" + getUserId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role='" + getRole() + '\'' +
                '}';
    }
}