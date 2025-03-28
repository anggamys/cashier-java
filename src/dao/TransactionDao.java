package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import models.Transaction;

public class TransactionDao {
    public int addTransaction(Transaction transaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (customer_name, total_price, date) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaction.getCustomerName());
            stmt.setInt(2, transaction.getTotalPrice());
            stmt.setTimestamp(3, Timestamp.valueOf(
                transaction.getDate() != null ? transaction.getDate() : LocalDateTime.now()
            ));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to add transaction: " + e.getMessage());
            throw e;
        }
        return -1;
    }

    public void updateTotalPrice(int transactionId, int totalPrice, Connection conn) throws SQLException {
        String sql = "UPDATE transactions SET total_price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalPrice);
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to update transaction total price: " + e.getMessage());
            throw e;
        }
    }

    public Transaction getTransactionById(int id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Transaction(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getInt("total_price"),
                        rs.getTimestamp("date").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch transaction: " + e.getMessage());
            throw e;
        }
        return null;
    }
}
