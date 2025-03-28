package dao;

import model.ItemTransaction;
import java.sql.*;

public class ItemTransactionDao {
    public void addItemTransaction(ItemTransaction itemTransaction) {
        String selectPriceQuery = "SELECT price FROM products WHERE id = ?";
        String insertItemTransactionQuery = "INSERT INTO item_transactions (transaction_id, product_id, quantity, amount) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false); // 🔴 Matikan auto-commit untuk atomicity

            int pricePerItem = 0;

            try (PreparedStatement stmt = connection.prepareStatement(selectPriceQuery)) {
                stmt.setInt(1, itemTransaction.getProductId());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        pricePerItem = rs.getInt("price");
                    } else {
                        System.out.println("❌ Product not found!");
                        connection.rollback(); // 🚨 Batalkan jika produk tidak ditemukan
                        return;
                    }
                }
            }

            // ✅ Hitung total amount
            itemTransaction.setAmount(itemTransaction.getQuantity() * pricePerItem);

            // 🔽 Masukkan transaksi item
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertItemTransactionQuery)) {
                preparedStatement.setInt(1, itemTransaction.getTransactionId());
                preparedStatement.setInt(2, itemTransaction.getProductId());
                preparedStatement.setInt(3, itemTransaction.getQuantity());
                preparedStatement.setInt(4, itemTransaction.getAmount());

                preparedStatement.executeUpdate();
            }

            connection.commit(); // ✅ Commit transaksi jika semua sukses
            System.out.println("✅ Item transaction added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                // 🔴 Rollback jika terjadi error
                System.out.println("⚠️ Rolling back transaction...");
                DatabaseConnection.getConnection().rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        } finally {
            try {
                // ✅ Pastikan auto-commit dihidupkan kembali
                DatabaseConnection.getConnection().setAutoCommit(true);
            } catch (SQLException ignored) {}
        }
    }
}
