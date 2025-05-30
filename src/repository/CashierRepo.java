package repository;

import java.sql.*;
import models.*;
import utils.*;

public class CashierRepo {

    private static final String ADD_CASHIER = "INSERT INTO cashier (id, name, email, phone, address, username, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_CASHIER_BY_USERNAME = "SELECT * FROM cashier WHERE username = ?";
    private static final String GET_CASHIER_BY_ID = "SELECT * FROM cashier WHERE id = ?";
    private static final String GET_ALL_CASHIERS = "SELECT * FROM cashier";
    private static final String UPDATE_CASHIER = "UPDATE cashier SET name = ?, email = ?, phone = ?, address = ?, username = ?, password = ? WHERE id = ?";
    private static final String DELETE_CASHIER = "DELETE FROM cashier WHERE id = ?";
    private static final int MAX_CASHIERS = 100;

    public Cashier addCashier(final Cashier cashier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_CASHIER)) {

            stmt.setString(1, cashier.getId());
            stmt.setString(2, cashier.getName());
            stmt.setString(3, cashier.getEmail());
            stmt.setString(4, cashier.getPhoneNumber());
            stmt.setString(5, cashier.getAddress());
            stmt.setString(6, cashier.getUsername());
            stmt.setString(7, cashier.getPassword());

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0 ? cashier : null;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "addCashier", e);
            return null;
        }
    }

    public Cashier getCashierByUsername(final String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CASHIER_BY_USERNAME)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? mapResultSetToCashier(rs) : null;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "getCashierByUsername", e);
            return null;
        }
    }

    public Cashier getCashierById(final String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_CASHIER_BY_ID)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? mapResultSetToCashier(rs) : null;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "getCashierById", e);
            return null;
        }
    }

    public Cashier[] getAllCashiers() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_CASHIERS);
             ResultSet rs = stmt.executeQuery()) {

            Cashier[] cashiers = new Cashier[MAX_CASHIERS];
            int index = 0;

            while (rs.next() && index < MAX_CASHIERS) {
                cashiers[index++] = mapResultSetToCashier(rs);
            }

            return cashiers;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "getAllCashiers", e);
            return null;
        }
    }

    public Cashier updateCashier(final Cashier cashier) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_CASHIER)) {

            stmt.setString(1, cashier.getName());
            stmt.setString(2, cashier.getEmail());
            stmt.setString(3, cashier.getPhoneNumber());
            stmt.setString(4, cashier.getAddress());
            stmt.setString(5, cashier.getUsername());
            stmt.setString(6, cashier.getPassword());
            stmt.setString(7, cashier.getId());

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0 ? cashier : null;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "updateCashier", e);
            return null;
        }
    }

    public boolean deleteCashier(final String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_CASHIER)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            FormatUtil.logError("CashierRepo", "deleteCashier", e);
            return false;
        }
    }

    private Cashier mapResultSetToCashier(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String address = rs.getString("address");
        String username = rs.getString("username");
        String password = rs.getString("password");

        return new Cashier(id, name, email, phone, address, username, password);
    }
}
