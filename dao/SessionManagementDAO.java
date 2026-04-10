package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SessionManagementDAO {

    public List<String[]> getAllSessions() {
        List<String[]> sessions = new ArrayList<String[]>();

        String sql = "SELECT id, session_date, time, level, capacity " +
                "FROM class_sessions " +
                "ORDER BY session_date ASC, time ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] row = new String[5];
                row[0] = String.valueOf(rs.getInt("id"));
                row[1] = String.valueOf(rs.getDate("session_date"));
                row[2] = rs.getString("time");
                row[3] = rs.getString("level");
                row[4] = String.valueOf(rs.getInt("capacity"));
                sessions.add(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sessions;
    }

    public boolean deleteSession(int sessionId) {
        String sql = "DELETE FROM class_sessions WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sessionId);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}