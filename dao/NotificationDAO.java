package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public boolean createNotification(String email, String message, String type) {
        String userSql = "SELECT id FROM users WHERE email = ?";
        String notificationSql = "INSERT INTO notifications (user_id, message, type, is_read) VALUES (?, ?, ?, FALSE)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement userStmt = conn.prepareStatement(userSql)) {

            userStmt.setString(1, email);

            try (ResultSet rs = userStmt.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }

                int userId = rs.getInt("id");

                try (PreparedStatement notifStmt = conn.prepareStatement(notificationSql)) {
                    notifStmt.setInt(1, userId);
                    notifStmt.setString(2, message);
                    notifStmt.setString(3, type);

                    return notifStmt.executeUpdate() > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sendManualNotification(String email, String message) {
        return createNotification(email, message, "Manual");
    }

    public List<String[]> getNotificationsByEmail(String email) {
        List<String[]> notifications = new ArrayList<String[]>();

        String sql = "SELECT n.id, n.message, n.type, n.is_read " +
                "FROM notifications n " +
                "JOIN users u ON n.user_id = u.id " +
                "WHERE u.email = ? " +
                "ORDER BY n.id DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[4];
                    row[0] = String.valueOf(rs.getInt("id"));
                    row[1] = rs.getString("message");
                    row[2] = rs.getString("type");
                    row[3] = String.valueOf(rs.getBoolean("is_read"));
                    notifications.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notifications;
    }

    public boolean markAsRead(int notificationId) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notificationId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}