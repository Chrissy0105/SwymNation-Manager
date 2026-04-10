package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClassSessionDAO {

    public boolean createSession(String sessionDate, String time, String level, int capacity) {
        String sql = "INSERT INTO class_sessions (session_date, time, level, capacity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sessionDate);
            stmt.setString(2, time);
            stmt.setString(3, level);
            stmt.setInt(4, capacity);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}