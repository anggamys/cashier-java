package dao;

import model.Transaction;
import java.sql.*;
import java.time.LocalDateTime;

public class TransactionDao {
    private Connection conn;

    public TransactionDao() {
        this.conn = DatabaseConnection.getConnection();
    }

    public void beginTransaction() throws SQLException {
        conn.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        conn.commit();
        conn.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        conn.rollback();
        conn.setAutoCommit(true);
    }

    public int addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (customer_name, total_price, date) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, transaction.getCustomerName());
            stmt.setInt(2, transaction.getTotalPrice());

            // Jika date null, gunakan waktu sekarang
            LocalDateTime dateTime = transaction.getDate() != null ? transaction.getDate() : LocalDateTime.now();
            stmt.setTimestamp(3, Timestamp.valueOf(dateTime));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Mengembalikan ID transaksi yang baru dibuat
                    }
                }
            }
        }
        return -1; // Jika gagal
    }


    public void updateTotalPrice(int transactionId, int totalPrice) throws SQLException {
        String sql = "UPDATE transactions SET total_price = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, totalPrice);
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        }
    }

    public Transaction getTransactionById(int transactionId) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setCustomerName(rs.getString("customer_name"));
                    transaction.setTotalPrice(rs.getInt("total_price"));
                    transaction.setDate(rs.getTimestamp("date").toLocalDateTime());
                    return transaction;
                }
            }
        }
        
        return null;
    }
}
