package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientListDAO {

    public List<String> getAllClients() {
        List<String> clients = new ArrayList<String>();

        String sql = "SELECT name, email FROM users WHERE role = 'Client' ORDER BY name ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                clients.add(name + " - " + email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }
}