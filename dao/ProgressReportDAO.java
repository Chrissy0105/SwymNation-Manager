package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProgressReportDAO {

    public boolean saveProgressReport(String email, String performanceLevel, String comments) {
        String sql = "INSERT INTO student_progress (performance_level, comments, user_id) " +
                "VALUES (?, ?, " +
                "(SELECT id FROM users WHERE email = ? AND role = 'Client'))";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, performanceLevel);
            stmt.setString(2, comments);
            stmt.setString(3, email);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}