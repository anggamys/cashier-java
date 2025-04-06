package repository;

import java.sql.*;
import models.*;

public class CashierRepo {

    private static final String ADD_CASHIER = "INSERT INTO cashier (id, name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_CASHIER_BY_USERNAME = "SELECT * FROM cashier WHERE username = ?";    
    private static final String GET_CASHIER_BY_ID = "SELECT * FROM cashier WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM cashier";

    public Cashier addCashier(Cashier newCashier){
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_CASHIER)) {

            stmt.setString(1, newCashier.getId());
            stmt.setString(2, newCashier.getName());
            stmt.setString(3, newCashier.getEmail());
            stmt.setInt(4, newCashier.getPhoneNumber());
            stmt.setString(5, newCashier.getAddress());
            stmt.setString(6, newCashier.getUsername());
            stmt.setString(7, newCashier.getPassword());

            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                return newCashier;
            } else {
                System.out.println("❌ Failed to add cashier.");
                return null;

            } 
        } catch (SQLException e) {
            System.out.println("❌ Error adding cashier: " + e.getMessage());
            return null;
        }
    }

    public Cashier getCashierByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CASHIER_BY_USERNAME)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String password = rs.getString("password");

                return new Cashier(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Cashier not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving cashier: " + e.getMessage());
            return null;
        }
    }

    public Cashier getCashierById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CASHIER_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                return new Cashier(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Cashier not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving cashier: " + e.getMessage());
            return null;
        }
    }

    public Cashier[] getAllCashiers() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            Cashier[] cashiers = new Cashier[100]; // Assuming a maximum of 100 cashiers
            int index = 0;

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                cashiers[index++] = new Cashier(id, name, email, phoneNumber, address, username, password);
            }

            return cashiers;
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving all cashiers: " + e.getMessage());
            return null;
        }
    }
}