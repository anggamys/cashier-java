package commands;

import models.*;
import services.*;
import utils.*;

public class TransaksiHandler {

    private final TransactionService transactionService;
    private final MakananHandler makananHandler;
    private final MinumanHandler minumanHandler;
    private final MakananService makananService;
    private final MinumanService minumanService;

    public TransaksiHandler() {
        this.transactionService = new TransactionService();
        this.makananHandler = new MakananHandler();
        this.minumanHandler = new MinumanHandler();
        this.makananService = new MakananService();
        this.minumanService = new MinumanService();
    }

    public void addTransaction() {
        InterfaceUtil.clearScreen();
        printHeader("TRANSAKSI BARU");

        System.out.println(">> Menu Makanan:");
        makananHandler.lihatMenuMakanan();

        System.out.println(">> Menu Minuman:");
        minumanHandler.lihatMenuMinuman();

        String customerName  = FormHandler.stringForm("Nama Pelanggan           : ").trim();
        int jumlahPesanan    = FormHandler.integerForm("Jumlah Item yang Dipesan : ");

        if (jumlahPesanan <= 0) {
            System.out.println("❌ Jumlah item tidak boleh kurang dari 1.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        String[] itemIds       = new String[jumlahPesanan];
        int[]    itemQuantities = new int[jumlahPesanan];
        String[] itemNames     = new String[jumlahPesanan];

        System.out.println("\n>> Masukkan Detail Pesanan:");
        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.println("- Pesanan ke-" + (i + 1));

            String itemId  = FormHandler.stringForm("   ID Item    : ").trim();
            int quantity   = FormHandler.integerForm("   Jumlah     : ");

            if (quantity <= 0) {
                System.out.println("❌ Jumlah harus lebih dari 0.");
                i--;
                continue;
            }

            String itemName = getItemNameById(itemId);
            if (itemName == null) {
                System.out.println("❌ Item tidak ditemukan atau tidak tersedia. Silakan ulangi.");
                i--;
                continue;
            }

            itemIds[i]       = itemId;
            itemQuantities[i] = quantity;
            itemNames[i]     = itemName;
        }

        if (!konfirmasiPesanan(customerName, itemNames, itemQuantities)) {
            System.out.println("\n❌ Transaksi dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Transaction newTransaction = transactionService.addTransaction(customerName, itemIds, itemQuantities);

        System.out.println();
        if (newTransaction != null) {
            System.out.println("✅ Transaksi berhasil disimpan!");
            System.out.println("ID Transaksi  : " + newTransaction.getId());
            System.out.println("Total Bayar   : " + FormatUtil.formatCurrency(newTransaction.getTotalAmount()));
        } else {
            System.out.println("❌ Terjadi kesalahan saat menyimpan transaksi.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    private boolean konfirmasiPesanan(String customerName, String[] itemNames, int[] quantities) {
        printHeader("KONFIRMASI PESANAN");

        System.out.println("Nama Pelanggan : " + customerName);
        System.out.println("Detail Pesanan :");
        for (int i = 0; i < itemNames.length; i++) {
            System.out.println("  - " + itemNames[i] + " (x" + quantities[i] + ")");
        }
        System.out.println("=================================");

        boolean isConfirmed = FormHandler.confirmationForm("Apakah Anda yakin dengan pesanan ini? (y/n) : ");

        if (isConfirmed) {
            System.out.println("✅ Pesanan dikonfirmasi.");
            return true;
        } else {
            System.out.println("❌ Pesanan dibatalkan.");
            return false;
        }
    }

    private String getItemNameById(String id) {
        Makanan makanan = makananService.getMakananById(id);
        if (makanan != null && makanan.getIsReady()) {
            return makanan.getName();
        }

        Minuman minuman = minumanService.getMinumanById(id);
        if (minuman != null && minuman.getIsReady()) {
            return minuman.getName();
        }

        return null;
    }

    private void printHeader(String title) {
        System.out.println("=================================");
        System.out.printf("         %-23s%n", title);
        System.out.println("=================================\n");
    }
}
