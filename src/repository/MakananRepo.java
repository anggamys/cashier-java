package repository;

import java.sql.*;
import models.*;
import utils.*;

public class MakananRepo {

    private static final String ADD_MAKANAN = "INSERT INTO makanan (id, name, category, harga, is_ready) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_MAKANAN_BY_ID = "SELECT * FROM makanan WHERE id = ?";
    private static final String GET_ALL_MAKANAN = "SELECT * FROM makanan";
    private static final String UPDATE_MAKANAN = "UPDATE makanan SET name = ?, category = ?, harga = ?, is_ready = ? WHERE id = ?";
    private static final String DELETE_MAKANAN = "DELETE FROM makanan WHERE id = ?";
    private static final int MAX_MAKANAN = 100;

    public Makanan addMakanan(Makanan newMakanan) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_MAKANAN)) {

            stmt.setString(1, newMakanan.getId());
            stmt.setString(2, newMakanan.getName());
            stmt.setString(3, newMakanan.getCategory());
            stmt.setInt(4, newMakanan.getHarga());
            stmt.setBoolean(5, newMakanan.getIsReady());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0 ? newMakanan : null;

        } catch (SQLException e) {
            FormatUtil.logError("MakananRepo", "addMakanan", e);
            return null;
        }
    }

    public Makanan getMakananById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MAKANAN_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? mapResultSetToMakanan(rs) : null;

        } catch (SQLException e) {
            FormatUtil.logError("MakananRepo", "getMakananById", e);
            return null;
        }
    }

    public Makanan[] getAllMakanan() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_MAKANAN);
             ResultSet rs = stmt.executeQuery()) {

            Makanan[] list = new Makanan[MAX_MAKANAN];
            int index = 0;

            while (rs.next() && index < MAX_MAKANAN) {
                list[index++] = mapResultSetToMakanan(rs);
            }

            return list;

        } catch (SQLException e) {
            FormatUtil.logError("MakananRepo", "getAllMakanan", e);
            return null;
        }
    }

    public Makanan updateMakanan(Makanan updatedMakanan) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_MAKANAN)) {

            stmt.setString(1, updatedMakanan.getName());
            stmt.setString(2, updatedMakanan.getCategory());
            stmt.setInt(3, updatedMakanan.getHarga());
            stmt.setBoolean(4, updatedMakanan.getIsReady());
            stmt.setString(5, updatedMakanan.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0 ? updatedMakanan : null;

        } catch (SQLException e) {
            FormatUtil.logError("MakananRepo", "updateMakanan", e);
            return null;
        }
    }

    public boolean deleteMakanan(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_MAKANAN)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            FormatUtil.logError("MakananRepo", "deleteMakanan", e);
            return false;
        }
    }

    private Makanan mapResultSetToMakanan(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String category = rs.getString("category");
        int harga = rs.getInt("harga");
        boolean isReady = rs.getBoolean("is_ready");

        return new Makanan(id, name, category, harga, isReady);
    }
}
