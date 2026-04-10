package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClientClassScheduleDAO {

    public boolean assignClientToSession(String email, int classSessionId) {
        String sql = "INSERT INTO client_class_schedule (user_id, class_session_id) " +
                "VALUES ((SELECT id FROM users WHERE email = ? AND role = 'Client'), ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, classSessionId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}