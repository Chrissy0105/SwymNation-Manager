package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReceiptDAO {

    public PaymentResult getReceiptByPaymentId(int paymentId) {
        String sql = "SELECT r.id AS receipt_id, p.id AS payment_id, p.amount, p.payment_method, u.email " +
                "FROM receipts r " +
                "JOIN payments p ON r.payment_id = p.id " +
                "JOIN users u ON p.user_id = u.id " +
                "WHERE p.id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PaymentResult(
                            true,
                            rs.getInt("payment_id"),
                            rs.getInt("receipt_id"),
                            rs.getDouble("amount"),
                            rs.getString("payment_method"),
                            rs.getString("email"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PaymentResult(false, 0, 0, 0.0, "", "");
    }
}