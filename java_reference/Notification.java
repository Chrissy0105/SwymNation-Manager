package java_reference;

public class Notification {
    private int notificationId;
    private String message;
    private String type;
    private boolean isRead;
    
    public Notification(int notificationId, String message, String type) {
        this.notificationId = notificationId;
        this.message = message;
        this.type = type;
        this.isRead = false;
    }

    public void sendNotification(Client client) {
        if (client == null) {
            System.out.println("Invalid client.");
            return;
        }

        if (message == null || message.trim().isEmpty()) {
            System.out.println("Notification message cannot be empty.");
            return;
        }

        if (type == null || type.trim().isEmpty()) {
            System.out.println("Notification type cannot be empty.");
            return;
        }

        client.receiveNotification(message);
        System.out.println("Notification sent to: " + client.getName());
    }

    public void markAsRead() {
        isRead = true;
        System.out.println("Notification ID " + notificationId + " marked as read.");
    }

    public int getNotificationId() {
        return notificationId;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public boolean isRead() {
        return isRead;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", isRead=" + isRead +
                '}';
    }
}