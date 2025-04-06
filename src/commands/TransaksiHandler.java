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
        System.out.println("=================================");
        System.out.println("         TRANSAKSI BARU          ");
        System.out.println("=================================\n");

        System.out.println(">> Menu Makanan:");
        makananHandler.lihatMenuMakanan();

        System.out.println(">> Menu Minuman:");
        minumanHandler.lihatMenuMinuman();

        String customerName = FormHandler.stringForm("Nama Pelanggan           : ");
        int jumlahPesanan = FormHandler.integerForm("Jumlah Item yang Dipesan : ");

        String[] itemIds = new String[jumlahPesanan];
        int[] itemQuantities = new int[jumlahPesanan];
        String[] itemNames = new String[jumlahPesanan];

        System.out.println("\n>> Masukkan Detail Pesanan:");
        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.println("- Pesanan ke-" + (i + 1));
            String itemId = FormHandler.stringForm("   ID Item    : ");
            int quantity = FormHandler.integerForm("   Jumlah     : ");

            String itemName = getItemNameById(itemId);

            if (itemName == null) {
                System.out.println("❌ Item tidak ditemukan. Silakan ulangi.");
                i--; // ulangi input untuk indeks ini
                continue;
            }

            itemIds[i] = itemId;
            itemQuantities[i] = quantity;
            itemNames[i] = itemName;
        }

        System.out.println("\n=================================");
        System.out.println("        KONFIRMASI PESANAN       ");
        System.out.println("=================================");
        System.out.println("Nama Pelanggan : " + customerName);
        System.out.println("Detail Pesanan :");
        for (int i = 0; i < jumlahPesanan; i++) {
            System.out.println("  - " + itemNames[i] + " (x" + itemQuantities[i] + ")");
        }

        System.out.println("=================================");
        String konfirmasi = FormHandler.stringForm("Lanjutkan transaksi? (y/n): ");

        if (!konfirmasi.equalsIgnoreCase("y")) {
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
}
