package services;

import dao.TransactionDao;
import dao.DatabaseConnection;
import dao.ItemTransactionDao;
import dao.ProductDao;
import dao.TransactionManager;
import models.ItemTransaction;
import models.Product;
import models.Transaction;

import java.sql.SQLException;

public class TransactionService {
    private final TransactionDao transactionDao;
    private final ItemTransactionDao itemTransactionDao;
    private final ProductDao productDao;

    public TransactionService() {
        this.transactionDao = new TransactionDao();
        this.itemTransactionDao = new ItemTransactionDao();
        this.productDao = new ProductDao();
    }

    public int addTransaction(String customerName, int[] productIds, int[] quantities) {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            Transaction transaction = new Transaction();
            transaction.setCustomerName(customerName);
            transaction.setTotalPrice(0);

            int transactionId = transactionDao.addTransaction(transaction, tx.getConnection());
            if (transactionId == -1) {
                System.out.println("❌ Failed to create transaction!");
                return -1;
            }

            int totalPrice = processItems(transactionId, productIds, quantities, tx);
            if (totalPrice == -1) return -1; 
            
            transactionDao.updateTotalPrice(transactionId, totalPrice, tx.getConnection());
            tx.commit();

            System.out.println("✅ Transaction added successfully! ID: " + transactionId);
            return transactionId;
        } catch (SQLException e) {
            System.out.println("❌ Failed to create transaction: " + e.getMessage());
            return -1;
        }
    }

    private int processItems(int transactionId, int[] productIds, int[] quantities, TransactionManager tx) throws SQLException {
        int totalPrice = 0;

        for (int i = 0; i < productIds.length; i++) {
            Product product = productDao.getProductById(productIds[i], tx.getConnection());
            if (product == null) {
                System.out.println("❌ Product ID " + productIds[i] + " not found!");
                return -1;
            }

            if (product.getStock() < quantities[i]) {
                System.out.println("❌ Insufficient stock for product ID " + productIds[i]);
                return -1;
            }

            int itemPrice = product.getPrice() * quantities[i];
            totalPrice += itemPrice;

            ItemTransaction itemTransaction = new ItemTransaction(0, transactionId, productIds[i], quantities[i], itemPrice);
            itemTransactionDao.addItemTransaction(itemTransaction, tx.getConnection());
            productDao.updateStockProduct(productIds[i], quantities[i], tx.getConnection());
        }

        return totalPrice;
    }

    public Transaction getTransactionById(int id) {
        try (TransactionManager tx = new TransactionManager(DatabaseConnection.getConnection())) {
            return transactionDao.getTransactionById(id, tx.getConnection());
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch transaction: " + e.getMessage());
            return null;
        }
    }
}
