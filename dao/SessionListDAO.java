package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionListDAO {

    public List<String> getAllSessions() {
        List<String> sessions = new ArrayList<String>();

        String sql = "SELECT id, session_date, time, level, capacity " +
                "FROM class_sessions " +
                "ORDER BY session_date ASC, time ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String item = "Session " + rs.getInt("id") +
                        " - " + rs.getString("level") +
                        " - " + rs.getDate("session_date") +
                        " - " + rs.getString("time") +
                        " - Capacity: " + rs.getInt("capacity");

                sessions.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessions;
    }
}