package services;

import repository.*;
import models.*;
import utils.*;

import java.time.*;

public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final ItemTransactionRepo itemTransactionRepo;
    private final MakananRepo makananRepo;
    private final MinumanRepo minumanRepo;

    public TransactionService() {
        this.transactionRepo = new TransactionRepo();
        this.itemTransactionRepo = new ItemTransactionRepo();
        this.makananRepo = new MakananRepo();
        this.minumanRepo = new MinumanRepo();
    }

    public Transaction addTransaction(String customerName, String[] itemIds, int[] quantities) {
        if (customerName == null || customerName.isBlank()) {
            System.out.println("❌ Nama pelanggan tidak boleh kosong.");
            return null;
        }

        if (itemIds == null || quantities == null || itemIds.length != quantities.length || itemIds.length == 0) {
            System.out.println("❌ Daftar item atau kuantitas tidak valid.");
            return null;
        }

        for (int qty : quantities) {
            if (qty <= 0) {
                System.out.println("❌ Kuantitas harus lebih dari 0.");
                return null;
            }
        }

        String transactionId = FormatUtil.generateUniqueID();
        Transaction transaction = new Transaction(transactionId, customerName.trim(), 0, LocalDateTime.now());

        try {
            Transaction createdTransaction = transactionRepo.addTransaction(transaction);
            if (createdTransaction == null) {
                System.out.println("❌ Gagal membuat transaksi.");
                return null;
            }

            int totalAmount = processItems(transactionId, itemIds, quantities);
            if (totalAmount < 0) {
                System.out.println("❌ Gagal memproses item transaksi.");
                return null;
            }

            transactionRepo.updateTransactionAmount(transactionId, totalAmount);
            return transactionRepo.getTransactionById(transactionId);

        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "addTransaction", e);
            return null;
        }
    }

    public Transaction[] getAllTransactions() {
        try {
            Transaction[] transactions = transactionRepo.getAllTransactions();
            if (transactions.length == 0) {
                System.out.println("⚠️  Tidak ada transaksi yang ditemukan.");
                return null;
            }
            return transactions;
        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "getAllTransactions", e);
            return null;
        }
    }

    public Transaction getTransactionById(String transactionId) {
        try {
            Transaction transaction = transactionRepo.getTransactionById(transactionId);
            if (transaction != null) {
                return transaction;
            } else {
                System.out.println("⚠️  Transaksi tidak ditemukan.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "getTransactionById", e);
            return null;
        }
    }

    public ItemTransaction getItemTransactionById(String itemTransactionId) {
        try {
            ItemTransaction itemTransaction = itemTransactionRepo.getItemTransactionById(itemTransactionId);
            if (itemTransaction != null) {
                return itemTransaction;
            } else {
                System.out.println("⚠️  Item transaksi tidak ditemukan.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "getItemTransactionById", e);
            return null;
        }
    }

    public TransactionSummary getTransactionSummary() {
        Transaction[] transactions = transactionRepo.getAllTransactions();
    
        if (transactions.length == 0) {
            System.out.println("⚠️  Tidak ada transaksi yang ditemukan.");
            return new TransactionSummary(0, 0, null);
        }
    
        int totalIncome = 0;
        for (Transaction t : transactions) {
            totalIncome += t.getTotalAmount();
        }
    
        Transaction latestTransaction = transactions[0];
        
        return new TransactionSummary(transactions.length, totalIncome, latestTransaction);
    }
    

    private int processItems(String transactionId, String[] itemIds, int[] quantities) {
        int totalAmount = 0;

        try {
            for (int i = 0; i < itemIds.length; i++) {
                String itemId = itemIds[i];
                int quantity = quantities[i];

                int price = getItemPrice(itemId);
                if (price < 0) {
                    System.out.println("❌ Item tidak ditemukan atau tidak tersedia: " + itemId);
                    return -1;
                }

                int subtotal = price * quantity;
                totalAmount += subtotal;

                String itemTransactionId = FormatUtil.generateUniqueID();
                ItemTransaction itemTransaction = new ItemTransaction(itemTransactionId, transactionId, itemId, quantity, subtotal);

                if (itemTransactionRepo.addItemTransaction(itemTransaction) == null) {
                    System.out.println("❌ Gagal menyimpan item transaksi: " + itemId);
                    return -1;
                }
            }

            return totalAmount;

        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "processItems", e);
            return -1;
        }
    }

    private int getItemPrice(String itemId) {
        Makanan makanan = makananRepo.getMakananById(itemId);
        if (makanan != null && makanan.getIsReady()) {
            return makanan.getHarga();
        }

        Minuman minuman = minumanRepo.getMinumanById(itemId);
        if (minuman != null && minuman.getIsReady()) {
            return minuman.getHarga();
        }

        return -1;
    }
}
