package repository;

import java.sql.*;
import java.util.*;

import models.*;
import utils.*;

public class OwnerRepo {

    private static final String INSERT_OWNER_SQL = "INSERT INTO owners (id, name, email, phone_number, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_SQL = "SELECT * FROM owners WHERE username = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM owners WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM owners";

    public Owner addOwner(Owner newOwner) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_OWNER_SQL)) {

            stmt.setString(1, newOwner.getId());
            stmt.setString(2, newOwner.getName());
            stmt.setString(3, newOwner.getEmail());
            stmt.setInt(4, newOwner.getPhoneNumber());
            stmt.setString(5, newOwner.getAddress());
            stmt.setString(6, newOwner.getUsername());
            stmt.setString(7, newOwner.getPassword());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return newOwner;
            }

            System.err.println("❌ Failed to add owner.");
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "addOwner", e);
        }
        return null;
    }

    public Owner getOwnerByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_SQL)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOwner(rs);
            }

            System.err.println("❌ Owner not found with username: " + username);
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "getOwnerByUsername", e);
        }
        return null;
    }

    public Owner getOwnerById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToOwner(rs);
            }

            System.err.println("❌ Owner not found with ID: " + id);
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "getOwnerById", e);
        }
        return null;
    }

    public Owner[] getAllOwners() {
        List<Owner> owners = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                owners.add(mapResultSetToOwner(rs));
            }

            return owners.toArray(new Owner[0]);
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "getAllOwners", e);
            return null;
        }
    }

    // Helper method
    private Owner mapResultSetToOwner(ResultSet rs) throws SQLException {
        return new Owner(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getInt("phone_number"),
            rs.getString("address"),
            rs.getString("username"),
            rs.getString("password")
        );
    }
}
