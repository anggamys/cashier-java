package repository;

import java.sql.*;

import models.*;

public class OwnerRepo {

    private static final String ADD_OWNER = "INSERT INTO owners (id, name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_OWNER_BY_USERNAME = "SELECT * FROM owners WHERE username = ?";
    private static final String GET_OWNER_BY_ID = "SELECT * FROM owners WHERE id = ?";
    private static final String GET_ALL = "SELECT * FROM owners";

    public Owner addOwner(Owner newOwner){
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_OWNER)) {

            stmt.setString(1, newOwner.getId());
            stmt.setString(2, newOwner.getName());
            stmt.setString(3, newOwner.getEmail());
            stmt.setInt(4, newOwner.getPhoneNumber());
            stmt.setString(5, newOwner.getAddress());
            stmt.setString(6, newOwner.getUsername());
            stmt.setString(7, newOwner.getPassword());

            int rowsInserted = stmt.executeUpdate();
            
            if (rowsInserted > 0) {
                System.out.println("✅ Owner added successfully!");
                return newOwner;
            } else {
                System.out.println("❌ Failed to add owner.");
                return null;

            } 
        } catch (SQLException e) {
            System.out.println("❌ Error adding owner: " + e.getMessage());
            return null;
        }
        
    }

    public Owner getOwnerByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_OWNER_BY_USERNAME)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String password = rs.getString("password");

                return new Owner(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Owner not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving owner: " + e.getMessage());
            return null;
        }
    }
    
    public Owner getOwnerById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_OWNER_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                return new Owner(id, name, email, phoneNumber, address, username, password);
            } else {
                System.out.println("❌ Owner not found.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving owner: " + e.getMessage());
            return null;
        }
    }

    public Owner[] getAllOwners() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL);
             ResultSet rs = stmt.executeQuery()) {

            Owner[] owners = new Owner[100]; // Assuming a maximum of 100 owners for simplicity
            int index = 0;

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int phoneNumber = rs.getInt("phone");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                owners[index++] = new Owner(id, name, email, phoneNumber, address, username, password);
            }

            return owners;
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving owners: " + e.getMessage());
            return null;
        }
    }
}