package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ScheduleNotificationDAO {

    public boolean createSmartNextClassNotification(String email) {
        String userSql = "SELECT id FROM users WHERE email = ? AND role = 'Client'";

        String nextClassSql = "SELECT cs.id, cs.session_date, cs.time, cs.level " +
                "FROM attendance_records ar " +
                "JOIN class_sessions cs ON ar.class_session_id = cs.id " +
                "WHERE ar.user_id = ? " +
                "AND cs.session_date >= CURRENT_DATE " +
                "ORDER BY cs.session_date ASC, cs.time ASC " +
                "LIMIT 1";

        String duplicateCheckSql = "SELECT id FROM notifications " +
                "WHERE user_id = ? AND type = 'Schedule' AND message = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement userStmt = conn.prepareStatement(userSql)) {

            userStmt.setString(1, email);

            try (ResultSet userRs = userStmt.executeQuery()) {
                if (!userRs.next()) {
                    return false;
                }

                int userId = userRs.getInt("id");

                try (PreparedStatement classStmt = conn.prepareStatement(nextClassSql)) {
                    classStmt.setInt(1, userId);

                    try (ResultSet classRs = classStmt.executeQuery()) {
                        if (!classRs.next()) {
                            return false;
                        }

                        int classId = classRs.getInt("id");
                        String sessionDate = String.valueOf(classRs.getDate("session_date"));
                        String time = classRs.getString("time");
                        String level = classRs.getString("level");

                        String message = "Reminder: Your next " + level +
                                " class is on " + sessionDate +
                                " at " + time +
                                ". [Session ID: " + classId + "]";

                        try (PreparedStatement duplicateStmt = conn.prepareStatement(duplicateCheckSql)) {
                            duplicateStmt.setInt(1, userId);
                            duplicateStmt.setString(2, message);

                            try (ResultSet dupRs = duplicateStmt.executeQuery()) {
                                if (dupRs.next()) {
                                    return true;
                                }
                            }
                        }

                        NotificationDAO notificationDAO = new NotificationDAO();
                        return notificationDAO.createNotification(email, message, "Schedule");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}