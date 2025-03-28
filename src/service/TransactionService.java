package service;

import model.*;
import dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final ItemTransactionDao itemTransactionDao;
    private final ProductDao productDao;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
        this.itemTransactionDao = new ItemTransactionDao();
        this.productDao = new ProductDao();
    }

    public Optional<Integer> addTransaction(String customerName, int[] productIds, int[] quantities) {
        Connection conn = DatabaseConnection.getConnection();
        
        try {
            conn.setAutoCommit(false); // ✅ Matikan auto-commit sebelum transaksi
    
            Transaction transaction = new Transaction();
            transaction.setCustomerName(customerName);
            transaction.setTotalPrice(0);
    
            int transactionId = transactionDao.addTransaction(transaction);
            if (transactionId == -1) {
                System.out.println("❌ Failed to create transaction!");
                conn.rollback(); // ❗ Rollback jika gagal
                return Optional.empty();
            }
    
            int totalPrice = 0;
            for (int i = 0; i < productIds.length; i++) {
                Product product = productDao.getProductById(productIds[i]);
                if (product == null) {
                    System.out.println("❌ Product ID " + productIds[i] + " not found!");
                    conn.rollback(); // ❗ Rollback jika produk tidak ditemukan
                    return Optional.empty();
                }
    
                if (product.getStock() < quantities[i]) {
                    System.out.println("❌ Insufficient stock for product ID " + productIds[i]);
                    conn.rollback(); // ❗ Rollback jika stok tidak mencukupi
                    return Optional.empty();
                }
    
                int itemPrice = product.getPrice() * quantities[i];
                totalPrice += itemPrice;
    
                ItemTransaction itemTransaction = new ItemTransaction();
                itemTransaction.setTransactionId(transactionId);
                itemTransaction.setProductId(productIds[i]);
                itemTransaction.setQuantity(quantities[i]);
                itemTransaction.setAmount(itemPrice);
    
                itemTransactionDao.addItemTransaction(itemTransaction);
                productDao.updateStockProduct(productIds[i], quantities[i]);
            }
    
            transactionDao.updateTotalPrice(transactionId, totalPrice);
            conn.commit(); // ✅ Commit transaksi setelah semua operasi sukses
    
            System.out.println("✅ Transaction added successfully! ID: " + transactionId);
            return Optional.of(transactionId); // ✅ Hanya mengembalikan ID transaksi
    
        } catch (SQLException e) {
            try {
                conn.rollback(); // ❗ Rollback jika ada error
                System.out.println("❌ Transaction failed. Rolling back...");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true); // ✅ Kembalikan auto-commit ke `true`
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    
        return Optional.empty();
    }
    
    public Optional<Transaction> getTransactionById(int transactionId) {
        try {
            return Optional.of(transactionDao.getTransactionById(transactionId));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
