package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class ClassSessionDAO {

    public boolean createSession(String sessionDate, String time, String level, int capacity) {
        String sql = "INSERT INTO class_sessions (session_date, time, level, capacity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            LocalDate parsedDate = LocalDate.parse(sessionDate.trim());

            stmt.setDate(1, Date.valueOf(parsedDate));
            stmt.setString(2, time.trim());
            stmt.setString(3, level.trim());
            stmt.setInt(4, capacity);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}