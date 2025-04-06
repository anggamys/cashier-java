package repository;

import java.sql.*;
import models.*;

public class PelangganRepo {

    private static final String ADD_PELANGGAN = "INSERT INTO pelanggan (id, name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_PELANGGAN_BY_USERNAME = "SELECT * FROM pelanggan WHERE username = ?";
    private static final String GET_PELANGGAN_BY_ID = "SELECT * FROM pelanggan WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM pelanggan";

    public Pelanggan addPelanggan(Pelanggan newPelanggan){
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_PELANGGAN)) {

            stmt.setString(1, newPelanggan.getId());
            stmt.setString(2, newPelanggan.getName());
            stmt.setString(3, newPelanggan.getEmail());
            stmt.setInt(4, newPelanggan.getPhoneNumber());
            stmt.setString(5, newPelanggan.getAddress());
            stmt.setString(6, newPelanggan.getUsername());
            stmt.setString(7, newPelanggan.getPassword());

            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("✅ Pelanggan added successfully!");
                return newPelanggan;
            } else {
                System.out.println("❌ Failed to add pelanggan.");
                return null;

            } 
        } catch (SQLException e) {
            System.out.println("❌ Error adding pelanggan: " + e.getMessage());
            return null;
        }
    }

    public Pelanggan getPelangganByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_PELANGGAN_BY_USERNAME)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String password = rs.getString("password");

                return new Pelanggan(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Pelanggan not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pelanggan: " + e.getMessage());
            return null;
        }
    }

    public Pelanggan getPelangganById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_PELANGGAN_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                return new Pelanggan(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Pelanggan not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving pelanggan: " + e.getMessage());
            return null;
        }
    }

    public Pelanggan[] getAllPelanggan() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            Pelanggan[] pelangganList = new Pelanggan[100]; // Assuming a maximum of 100 customers
            int index = 0;

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                pelangganList[index++] = new Pelanggan(id, name, email, phoneNumber, address, username, password);
            }

            return pelangganList;
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving all pelanggan: " + e.getMessage());
            return null;
        }
    }
}