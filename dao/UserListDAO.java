package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserListDAO {

    public List<String> getUsersByRole(String role) {
        List<String> users = new ArrayList<String>();

        String sql = "SELECT name, email FROM users WHERE role = ? ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    users.add(name + " - " + email);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<String>();

        String sql = "SELECT name, email, role FROM users ORDER BY role ASC, name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String role = rs.getString("role");
                users.add(name + " (" + role + ") - " + email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}