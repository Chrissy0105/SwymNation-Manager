package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PaymentDAO {

    public PaymentResult makePayment(String email, double amount, String method) {
        String userSql = "SELECT id FROM users WHERE email = ?";
        String paymentSql = "INSERT INTO payments (amount, status, created_at, user_id, payment_method) VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)";
        String paymentIdSql = "SELECT currval(pg_get_serial_sequence('payments','id')) AS payment_id";
        String receiptSql = "INSERT INTO receipts (issue_date, payment_id) VALUES (CURRENT_TIMESTAMP, ?)";
        String receiptIdSql = "SELECT currval(pg_get_serial_sequence('receipts','id')) AS receipt_id";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int userId;
            int paymentId;
            int receiptId;

            try (PreparedStatement userStmt = conn.prepareStatement(userSql)) {
                userStmt.setString(1, email);

                try (ResultSet rs = userStmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return new PaymentResult(false, 0, 0, amount, method, email);
                    }
                    userId = rs.getInt("id");
                }
            }

            try (PreparedStatement paymentStmt = conn.prepareStatement(paymentSql)) {
                paymentStmt.setDouble(1, amount);
                paymentStmt.setString(2, "Completed");
                paymentStmt.setInt(3, userId);
                paymentStmt.setString(4, method);
                paymentStmt.executeUpdate();
            }

            try (PreparedStatement paymentIdStmt = conn.prepareStatement(paymentIdSql);
                    ResultSet rs = paymentIdStmt.executeQuery()) {
                rs.next();
                paymentId = rs.getInt("payment_id");
            }

            try (PreparedStatement receiptStmt = conn.prepareStatement(receiptSql)) {
                receiptStmt.setInt(1, paymentId);
                receiptStmt.executeUpdate();
            }

            try (PreparedStatement receiptIdStmt = conn.prepareStatement(receiptIdSql);
                    ResultSet rs = receiptIdStmt.executeQuery()) {
                rs.next();
                receiptId = rs.getInt("receipt_id");
            }

            conn.commit();

            NotificationDAO notificationDAO = new NotificationDAO();
            notificationDAO.createNotification(
                    email,
                    "Payment of $" + amount + " was successfully made via " + method + ". Receipt ID: " + receiptId,
                    "Payment");

            return new PaymentResult(true, paymentId, receiptId, amount, method, email);

        } catch (Exception e) {
            e.printStackTrace();
            return new PaymentResult(false, 0, 0, amount, method, email);
        }
    }
}