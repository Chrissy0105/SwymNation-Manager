package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportDAO {

    public String generateFullSystemReport() {
        StringBuilder report = new StringBuilder();

        report.append("=== SWYM NATION SYSTEM REPORT ===\n\n");

        appendUserSection(report, "Client");
        appendUserSection(report, "Receptionist");
        appendUserSection(report, "Instructor");

        appendPaymentSummary(report);
        appendAttendanceSummary(report);
        appendSessionSummary(report);
        appendNotificationSummary(report);

        return report.toString();
    }

    private void appendUserSection(StringBuilder report, String role) {
        String sql = "SELECT id, name, email FROM users WHERE role = ? ORDER BY name ASC";

        report.append("=== ").append(role.toUpperCase()).append("S ===\n");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    report.append("ID: ").append(rs.getInt("id"))
                            .append(" | Name: ").append(rs.getString("name"))
                            .append(" | Email: ").append(rs.getString("email"))
                            .append("\n");
                }

                if (!found) {
                    report.append("No ").append(role.toLowerCase()).append(" records found.\n");
                }
            }

        } catch (Exception e) {
            report.append("Could not load ").append(role.toLowerCase()).append(" data.\n");
        }

        report.append("\n");
    }

    private void appendPaymentSummary(StringBuilder report) {
        String countSql = "SELECT COUNT(*) AS total_payments, COALESCE(SUM(amount), 0) AS total_amount FROM payments";
        String detailSql = "SELECT p.id, u.name, p.amount, p.status, p.payment_method " +
                "FROM payments p " +
                "JOIN users u ON p.user_id = u.id " +
                "ORDER BY p.id DESC";

        report.append("=== PAYMENTS SUMMARY ===\n");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement countStmt = conn.prepareStatement(countSql);
                ResultSet countRs = countStmt.executeQuery()) {

            if (countRs.next()) {
                report.append("Total Payments: ").append(countRs.getInt("total_payments")).append("\n");
                report.append("Total Amount: $").append(countRs.getDouble("total_amount")).append("\n\n");
            }

            try (PreparedStatement detailStmt = conn.prepareStatement(detailSql);
                    ResultSet rs = detailStmt.executeQuery()) {

                while (rs.next()) {
                    report.append("Payment ID: ").append(rs.getInt("id"))
                            .append(" | Name: ").append(rs.getString("name"))
                            .append(" | Amount: $").append(rs.getDouble("amount"))
                            .append(" | Status: ").append(rs.getString("status"))
                            .append(" | Method: ").append(rs.getString("payment_method"))
                            .append("\n");
                }
            }

        } catch (Exception e) {
            report.append("Could not load payment data.\n");
        }

        report.append("\n");
    }

    private void appendAttendanceSummary(StringBuilder report) {
        String sql = "SELECT a.id, u.name, a.status, a.date " +
                "FROM attendance_records a " +
                "JOIN users u ON a.user_id = u.id " +
                "ORDER BY a.id DESC";

        report.append("=== ATTENDANCE SUMMARY ===\n");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                report.append("Attendance ID: ").append(rs.getInt("id"))
                        .append(" | Name: ").append(rs.getString("name"))
                        .append(" | Status: ").append(rs.getString("status"))
                        .append(" | Date: ").append(rs.getDate("date"))
                        .append("\n");
            }

            if (!found) {
                report.append("No attendance records found.\n");
            }

        } catch (Exception e) {
            report.append("Could not load attendance data.\n");
        }

        report.append("\n");
    }

    private void appendSessionSummary(StringBuilder report) {
        String sql = "SELECT id, session_date, time, level, capacity " +
                "FROM class_sessions " +
                "ORDER BY session_date ASC, time ASC";

        report.append("=== CLASS SESSION SUMMARY ===\n");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                report.append("Session ID: ").append(rs.getInt("id"))
                        .append(" | Date: ").append(rs.getDate("session_date"))
                        .append(" | Time: ").append(rs.getString("time"))
                        .append(" | Level: ").append(rs.getString("level"))
                        .append(" | Capacity: ").append(rs.getInt("capacity"))
                        .append("\n");
            }

            if (!found) {
                report.append("No class sessions found.\n");
            }

        } catch (Exception e) {
            report.append("Could not load class session data.\n");
        }

        report.append("\n");
    }

    private void appendNotificationSummary(StringBuilder report) {
        String sql = "SELECT n.id, u.name, n.type, n.message, n.is_read " +
                "FROM notifications n " +
                "JOIN users u ON n.user_id = u.id " +
                "ORDER BY n.id DESC";

        report.append("=== NOTIFICATION SUMMARY ===\n");

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            boolean found = false;

            while (rs.next()) {
                found = true;
                report.append("Notification ID: ").append(rs.getInt("id"))
                        .append(" | Name: ").append(rs.getString("name"))
                        .append(" | Type: ").append(rs.getString("type"))
                        .append(" | Read: ").append(rs.getBoolean("is_read"))
                        .append("\n")
                        .append("Message: ").append(rs.getString("message"))
                        .append("\n\n");
            }

            if (!found) {
                report.append("No notifications found.\n");
            }

        } catch (Exception e) {
            report.append("Could not load notification data.\n");
        }

        report.append("\n");
    }
}