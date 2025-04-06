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
            System.out.println("===== MENU OWNER =====");
            System.out.println("1. Tambah Menu Makanan");
            System.out.println("2. Tambah Menu Minuman");
            System.out.println("3. Lihat Daftar Menu Makanan");
            System.out.println("4. Lihat Daftar Menu Minuman");
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> makananHandler.tambahMenuMakanan();
                case 2 -> minumanHandler.tambahMenuMinuman();
                case 3 -> makananHandler.lihatMenuMakanan();
                case 4 -> minumanHandler.lihatMenuMinuman();
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
            System.out.println("===== MENU KASIR =====");
            System.out.println("1. Lihat Daftar Menu Makanan");
            System.out.println("2. Lihat Daftar Menu Minuman");
            System.out.println("3. Proses Transaksi");
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> makananHandler.lihatMenuMakanan();
                case 2 -> minumanHandler.lihatMenuMinuman();
                case 3 -> transaksiHandler.addTransaction();
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

    public void pelangganMenu() {
        while (true) {
            InterfaceUtil.clearScreen();
            System.out.println("===== MENU PELANGGAN =====");
            System.out.println("1. Lihat Daftar Menu Makanan");
            System.out.println("2. Lihat Daftar Menu Minuman");
            System.out.println("0. Logout");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> makananHandler.lihatMenuMakanan();
                case 2 -> minumanHandler.lihatMenuMinuman();
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
}

