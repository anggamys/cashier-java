package commands;

import utils.*;

public class MenuHandler {

    private final AuthHandler authHandler;
    private final MakananHandler makananHandler;
    private final MinumanHandler minumanHandler;
    private final TransaksiHandler transaksiHandler;

    public MenuHandler() {
        this.authHandler = new AuthHandler();
        this.makananHandler = new MakananHandler();
        this.minumanHandler = new MinumanHandler();
        this.transaksiHandler = new TransaksiHandler();
    }

    public void ownerMenu() {
        while (true) {
            InterfaceUtil.clearScreen();
            System.out.println("=== MENU OWNER ===");
            System.out.println("1. Tambah Menu Makanan/Minuman");
            System.out.println("2. Lihat Daftar Menu");
            System.out.println("3. Ubah Menu");
            System.out.println("4. Hapus Menu");
            System.out.println("5. Lihat Daftar Transaksi");
            System.out.println("6. Lihat Ringkasan Transaksi");            
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> submenuTambahMenu();
                case 2 -> submenuLihatMenu();
                case 3 -> submenuUbahMenu();
                case 4 -> submenuHapusMenu();
                case 5 -> transaksiHandler.lihatRiwayatTransaksi();
                case 6 -> transaksiHandler.lihatSummaryTransaksi();
                case 0 -> {
                    authHandler.logout();
                    return;
                }
                default -> {
                    System.out.println("❌ Pilihan tidak valid.");
                    InterfaceUtil.pause(500);
                }
            }
        }
    }

    public void cashierMenu() {
        while (true) {
            InterfaceUtil.clearScreen();
            System.out.println("=== MENU KASIR ===");
            System.out.println("1. Lihat Menu Makanan/Minuman");
            System.out.println("2. Proses Transaksi");
            System.out.println("3. Lihat Daftar Transaksi");
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> submenuLihatMenu();
                case 2 -> transaksiHandler.addTransaction();
                case 3 -> transaksiHandler.lihatRiwayatTransaksi();
                case 0 -> {
                    authHandler.logout();
                    return;
                }
                default -> {
                    System.out.println("❌ Pilihan tidak valid.");
                    InterfaceUtil.pause(500);
                }
            }
            InterfaceUtil.pressEnterToContinue();
        }
    }

    public void pelangganMenu() {
        while (true) {
            InterfaceUtil.clearScreen();
            System.out.println("=== MENU PELANGGAN ===");
            System.out.println("1. Lihat Menu Makanan/Minuman");
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> submenuLihatMenu();
                case 0 -> {
                    authHandler.logout();
                    return;
                }
                default -> {
                    System.out.println("❌ Pilihan tidak valid.");
                    InterfaceUtil.pause(500);
                }
            }
            InterfaceUtil.pressEnterToContinue();
        }
    }

    private void submenuTambahMenu() {
        System.out.println("\n=== TAMBAH MENU ===");
        System.out.println("1. Tambah Makanan");
        System.out.println("2. Tambah Minuman");
        System.out.println("0. Kembali");

        int sub = FormHandler.integerForm("Pilih: ");
        switch (sub) {
            case 1 -> makananHandler.tambahMenuMakanan();
            case 2 -> minumanHandler.tambahMenuMinuman();
            case 0 -> {
                // Kembali ke menu utama
                return;
            }
            default -> System.out.println("❌ Pilihan tidak valid.");
        }
        InterfaceUtil.pressEnterToContinue();
    }

    private void submenuLihatMenu() {
        System.out.println("\n=== LIHAT MENU ===");
        System.out.println("1. Menu Makanan");
        System.out.println("2. Menu Minuman");
        System.out.println("0. Kembali");

        int sub = FormHandler.integerForm("Pilih: ");
        switch (sub) {
            case 1 -> makananHandler.lihatMenuMakanan();
            case 2 -> minumanHandler.lihatMenuMinuman();
            case 0 -> {
                // Kembali ke menu utama
                return;
            }
            default -> System.out.println("❌ Pilihan tidak valid.");
        }
        InterfaceUtil.pressEnterToContinue();
    }

    private void submenuUbahMenu() {
        System.out.println("\n=== UBAH MENU ===");
        System.out.println("1. Ubah Makanan");
        System.out.println("2. Ubah Minuman");
        System.out.println("0. Kembali");

        int sub = FormHandler.integerForm("Pilih: ");
        switch (sub) {
            case 1 -> makananHandler.ubahMenuMakanan();
            case 2 -> minumanHandler.ubahMenuMinuman();
            case 0 -> {
                // Kembali ke menu utama
                return;
            }
            default -> System.out.println("❌ Pilihan tidak valid.");
        }
        InterfaceUtil.pressEnterToContinue();
    }

    private void submenuHapusMenu() {
        System.out.println("\n=== HAPUS MENU ===");
        System.out.println("1. Hapus Makanan");
        System.out.println("2. Hapus Minuman");
        System.out.println("0. Kembali");

        int sub = FormHandler.integerForm("Pilih: ");
        switch (sub) {
            case 1 -> makananHandler.hapusMenuMakanan();
            case 2 -> minumanHandler.hapusMenuMinuman();
            case 0 -> {
                // Kembali ke menu utama
                return;
            }
            default -> System.out.println("❌ Pilihan tidak valid.");
        }
        InterfaceUtil.pressEnterToContinue();
    }
}