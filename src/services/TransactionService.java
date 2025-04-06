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

    public Transaction addTransaction(String customerName, String[] itemsId, int[] quantities) {
        String transactionId = FormatUtil.generateUniqueID();
    
        try {
            Transaction transaction = new Transaction(transactionId, customerName, 0, LocalDateTime.now());
            Transaction createdTransaction = transactionRepo.addTransaction(transaction);
    
            if (createdTransaction == null) {
                System.out.println("❌ Gagal membuat transaksi.");
                return null;
            }
    
            int totalAmount = processItems(transactionId, itemsId, quantities);
            if (totalAmount < 0) {
                System.out.println("❌ Gagal memproses item transaksi.");
                return null;
            }
    
            // Update total amount
            transactionRepo.updateTransactionAmount(transactionId, totalAmount);
    
            // Ambil transaksi yang telah diperbarui dari database
            Transaction updatedTransaction = transactionRepo.getTransactionById(transactionId);
            return updatedTransaction;
    
        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "addTransaction", e);
            return null;
        }
    }
    
    private int processItems(String transactionId, String[] itemsId, int[] quantities) {
        int totalAmount = 0;

        try {
            for (int i = 0; i < itemsId.length; i++) {
                String itemId = itemsId[i];
                int quantity = quantities[i];

                // Cek apakah makanan atau minuman
                Makanan makanan = makananRepo.getMakananById(itemId);
                Minuman minuman = minumanRepo.getMinumanById(itemId);

                int harga = 0;

                if (makanan != null && makanan.getIsReady()) {
                    harga = makanan.getHarga();
                } else if (minuman != null && minuman.getIsReady()) {
                    harga = minuman.getHarga();
                } else {
                    System.out.println("❌ Item tidak ditemukan atau tidak tersedia: " + itemId);
                    return -1;
                }

                int subtotal = harga * quantity;
                totalAmount += subtotal;

                // Buat dan simpan item transaksi
                String itemTransactionId = FormatUtil.generateUniqueID();
                ItemTransaction itemTransaction = new ItemTransaction(itemTransactionId, transactionId, itemId, quantity, subtotal);

                if (itemTransactionRepo.addItemTransaction(itemTransaction) == null) {
                    System.out.println("❌ Gagal menyimpan item transaksi untuk item: " + itemId);
                    return -1;
                }
            }

            return totalAmount;

        } catch (Exception e) {
            FormatUtil.logError("TransactionService", "processItems", e);
            return -1;
        }
    }
}
