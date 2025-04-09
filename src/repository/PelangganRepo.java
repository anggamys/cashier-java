package repository;

import java.sql.*;
import java.util.*;

import models.*;
import utils.*;

public class PelangganRepo {

    private static final String INSERT_PELANGGAN_SQL = "INSERT INTO pelanggan (id, name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_USERNAME_SQL = "SELECT * FROM pelanggan WHERE username = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM pelanggan WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM pelanggan";
    private static final String UPDATE_PELANGGAN_SQL = "UPDATE pelanggan SET name = ?, email = ?, phone = ?, address = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM pelanggan WHERE id = ?";

    public Pelanggan addPelanggan(Pelanggan newPelanggan) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_PELANGGAN_SQL)) {

            stmt.setString(1, newPelanggan.getId());
            stmt.setString(2, newPelanggan.getName());
            stmt.setString(3, newPelanggan.getEmail());
            stmt.setString(4, newPelanggan.getPhoneNumber());
            stmt.setString(5, newPelanggan.getAddress());
            stmt.setString(6, newPelanggan.getUsername());
            stmt.setString(7, newPelanggan.getPassword());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return newPelanggan;
            }

        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "addPelanggan", e);
        }
        return null;
    }

    public Pelanggan getPelangganByUsername(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_USERNAME_SQL)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPelanggan(rs);
            }

        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "getPelangganByUsername", e);
        }
        return null;
    }

    public Pelanggan getPelangganById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPelanggan(rs);
            }

        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "getPelangganById", e);
        }
        return null;
    }

    public Pelanggan[] getAllPelanggan() {
        List<Pelanggan> pelangganList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pelangganList.add(mapResultSetToPelanggan(rs));
            }

            return pelangganList.toArray(new Pelanggan[0]);
        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "getAllPelanggan", e);
            return null;
        }
    }

    public Pelanggan updatePelanggan(Pelanggan updatedPelanggan) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_PELANGGAN_SQL)) {
    
            stmt.setString(1, updatedPelanggan.getName());
            stmt.setString(2, updatedPelanggan.getEmail());
            stmt.setString(3, updatedPelanggan.getPhoneNumber());
            stmt.setString(4, updatedPelanggan.getAddress());
            stmt.setString(5, updatedPelanggan.getUsername());
            stmt.setString(6, updatedPelanggan.getPassword());
            stmt.setString(7, updatedPelanggan.getId());
    
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? updatedPelanggan : null;
    
        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "updatePelanggan", e);
            return null;
        }
    }
    
    public boolean deletePelangganById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_BY_ID_SQL)) {
    
            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
    
        } catch (SQLException e) {
            FormatUtil.logError("PelangganRepo", "deletePelangganById", e);
            return false;
        }
    }    

    // Helper
    private Pelanggan mapResultSetToPelanggan(ResultSet rs) throws SQLException {
        return new Pelanggan(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("address"),
            rs.getString("username"),
            rs.getString("password")
        );
    }
}
