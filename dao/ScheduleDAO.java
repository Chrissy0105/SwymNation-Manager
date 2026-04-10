package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

    public List<String[]> getClientScheduleByEmail(String email) {
        List<String[]> schedule = new ArrayList<String[]>();

        String sql = "SELECT cs.id, cs.session_date, cs.time, cs.level, cs.capacity " +
                "FROM client_class_schedule ccs " +
                "JOIN users u ON ccs.user_id = u.id " +
                "JOIN class_sessions cs ON ccs.class_session_id = cs.id " +
                "WHERE u.email = ? " +
                "ORDER BY cs.session_date ASC, cs.time ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[5];
                    row[0] = String.valueOf(rs.getInt("id"));
                    row[1] = String.valueOf(rs.getDate("session_date"));
                    row[2] = rs.getString("time");
                    row[3] = rs.getString("level");
                    row[4] = String.valueOf(rs.getInt("capacity"));
                    schedule.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return schedule;
    }
}