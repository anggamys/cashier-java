package repository;

import java.sql.*;
import java.time.*;
import java.util.*;

import models.*;
import utils.*;

public class TransactionRepo {

    private static final String ADD_TRANSACTION =
        "INSERT INTO transaction (id, customer_name, total_amount, date) VALUES (?, ?, ?, ?)";

    private static final String GET_TRANSACTION_BY_ID =
        "SELECT * FROM transaction WHERE id = ?";

    private static final String GET_ALL_TRANSACTIONS =
        "SELECT * FROM transaction ORDER BY date DESC";

    private static final String UPDATE_TRANSACTION =
        "UPDATE transaction SET customer_name = ?, total_amount = ?, date = ? WHERE id = ?";

    private static final String UPDATE_AMOUNT =
        "UPDATE transaction SET total_amount = ? WHERE id = ?";

    private static final String DELETE_TRANSACTION =
        "DELETE FROM transaction WHERE id = ?";

    public Transaction addTransaction(final Transaction transaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_TRANSACTION)) {

            stmt.setString(1, transaction.getId());
            stmt.setString(2, transaction.getCustomerName());
            stmt.setInt(3, transaction.getTotalAmount());
            stmt.setTimestamp(4, Timestamp.valueOf(transaction.getDate()));

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0 ? transaction : null;

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "addTransaction", e);
            return null;
        }
    }

    public Transaction getTransactionById(final String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_TRANSACTION_BY_ID)) {

            stmt.setString(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                } else {
                    System.out.println("❌ Transaction not found.");
                    return null;
                }
            }

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "getTransactionById", e);
            return null;
        }
    }

    public Transaction[] getAllTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_TRANSACTIONS);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                transactionList.add(mapResultSetToTransaction(rs));
            }

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "getAllTransactions", e);
        }

        return transactionList.toArray(new Transaction[0]); // konversi ke array
    }

    public boolean updateTransaction(final Transaction transaction) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_TRANSACTION)) {

            stmt.setString(1, transaction.getCustomerName());
            stmt.setInt(2, transaction.getTotalAmount());
            stmt.setTimestamp(3, Timestamp.valueOf(transaction.getDate()));
            stmt.setString(4, transaction.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "updateTransaction", e);
            return false;
        }
    }

    public void updateTransactionAmount(String id, int newAmount) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_AMOUNT)) {

            stmt.setInt(1, newAmount);
            stmt.setString(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "updateTransactionAmount", e);
        }
    }

    public boolean deleteTransactionById(String id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_TRANSACTION)) {

            stmt.setString(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            FormatUtil.logError("TransactionRepo", "deleteTransactionById", e);
            return false;
        }
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String customerName = rs.getString("customer_name");
        int amount = rs.getInt("total_amount");
        LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();

        return new Transaction(id, customerName, amount, date);
    }
}
