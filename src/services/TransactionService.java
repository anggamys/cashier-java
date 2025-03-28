package services;

import dao.TransactionDao;
import dao.DatabaseConnection;
import dao.ItemTransactionDao;
import dao.ProductDao;
import dao.TransactionManager;
import models.ItemTransaction;
import models.Product;
import models.Transaction;

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
        TransactionManager transactionManager = new TransactionManager(conn);

        try {
            transactionManager.beginTransaction();

            Transaction transaction = new Transaction();
            transaction.setCustomerName(customerName);
            transaction.setTotalPrice(0);

            int transactionId = transactionDao.addTransaction(transaction, conn);
            if (transactionId == -1) {
                System.out.println("❌ Failed to create transaction!");
                transactionManager.rollback();
                return Optional.empty();
            }

            int totalPrice = 0;
            for (int i = 0; i < productIds.length; i++) {
                Product product = productDao.getProductById(productIds[i], conn);
                if (product == null) {
                    System.out.println("❌ Product ID " + productIds[i] + " not found!");
                    transactionManager.rollback();
                    return Optional.empty();
                }

                if (product.getStock() < quantities[i]) {
                    System.out.println("❌ Insufficient stock for product ID " + productIds[i]);
                    transactionManager.rollback();
                    return Optional.empty();
                }

                int itemPrice = product.getPrice() * quantities[i];
                totalPrice += itemPrice;

                ItemTransaction itemTransaction = new ItemTransaction();
                itemTransaction.setTransactionId(transactionId);
                itemTransaction.setProductId(productIds[i]);
                itemTransaction.setQuantity(quantities[i]);
                itemTransaction.setAmount(itemPrice);

                itemTransactionDao.addItemTransaction(itemTransaction, conn);
                productDao.updateStockProduct(productIds[i], quantities[i], conn);
            }

            transactionDao.updateTotalPrice(transactionId, totalPrice, conn);
            transactionManager.commit();

            System.out.println("✅ Transaction added successfully! ID: " + transactionId);
            return Optional.of(transactionId);

        } catch (SQLException e) {
            try {
                transactionManager.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                transactionManager.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
        
        System.out.println("❌ Failed to create transaction!");
        return Optional.empty();
    }

    public Optional<Transaction> getTransactionById(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return Optional.ofNullable(transactionDao.getTransactionById(id, conn));
        } catch (SQLException e) {
            System.out.println("❌ Failed to fetch transaction: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
