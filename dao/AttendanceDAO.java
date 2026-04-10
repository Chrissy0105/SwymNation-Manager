package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AttendanceDAO {

    public boolean saveAttendance(String email, String status) {
        String findUserSql = "SELECT id FROM users WHERE email = ? AND role = 'Client'";
        String insertSql = "INSERT INTO attendance_records (date, status, user_id) VALUES (CURRENT_DATE, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement findStmt = conn.prepareStatement(findUserSql)) {

            findStmt.setString(1, email);

            try (ResultSet rs = findStmt.executeQuery()) {
                if (!rs.next()) {
                    return false;
                }

                int userId = rs.getInt("id");

                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, status);
                    insertStmt.setInt(2, userId);

                    return insertStmt.executeUpdate() > 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}