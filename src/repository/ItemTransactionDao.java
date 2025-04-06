package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.ItemTransaction;

public class ItemTransactionDao {
    public void addItemTransaction(ItemTransaction itemTransaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO item_transactions (transaction_id, product_id, quantity, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemTransaction.getTransactionId());
            stmt.setInt(2, itemTransaction.getProductId());
            stmt.setInt(3, itemTransaction.getQuantity());
            stmt.setInt(4, itemTransaction.getAmount());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to add item transaction: " + e.getMessage());
            throw e;
        }
    }
}
