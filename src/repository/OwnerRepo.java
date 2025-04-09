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
    private static final String UPDATE_OWNER_SQL = "UPDATE owners SET name = ?, email = ?, phone_number = ?, address = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM owners WHERE id = ?";

    public Owner addOwner(Owner newOwner) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_OWNER_SQL)) {

            stmt.setString(1, newOwner.getId());
            stmt.setString(2, newOwner.getName());
            stmt.setString(3, newOwner.getEmail());
            stmt.setString(4, newOwner.getPhoneNumber());
            stmt.setString(5, newOwner.getAddress());
            stmt.setString(6, newOwner.getUsername());
            stmt.setString(7, newOwner.getPassword());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return newOwner;
            }

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

    public Owner updateOwner(Owner updatedOwner) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_OWNER_SQL)) {
    
            stmt.setString(1, updatedOwner.getName());
            stmt.setString(2, updatedOwner.getEmail());
            stmt.setString(3, updatedOwner.getPhoneNumber());
            stmt.setString(4, updatedOwner.getAddress());
            stmt.setString(5, updatedOwner.getUsername());
            stmt.setString(6, updatedOwner.getPassword());
            stmt.setString(7, updatedOwner.getId());
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? updatedOwner : null;
            
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "updateOwner", e);
            return null;
        }
    }

    public boolean deleteOwnerById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID_SQL)) {
    
            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
    
        } catch (SQLException e) {
            FormatUtil.logError("OwnerRepo", "deleteOwnerById", e);
            return false;
        }
    }    

    // Helper method
    private Owner mapResultSetToOwner(ResultSet rs) throws SQLException {
        return new Owner(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone_number"),
            rs.getString("address"),
            rs.getString("username"),
            rs.getString("password")
        );
    }
}
