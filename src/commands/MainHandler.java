package commands;

import utils.*;

public class MainHandler {

    private final AuthHandler authHandler;
    private final SessionUtil sessionUtil;
    private final MenuHandler menuHandler;

    public MainHandler() {
        this.authHandler = new AuthHandler();
        this.menuHandler = new MenuHandler();
        this.sessionUtil = SessionUtil.getInstance();
    }

    public void start() {
        while (true) {
            InterfaceUtil.clearScreen();
            System.out.println("========================");
            System.out.println("        MENU UTAMA       ");
            System.out.println("========================");
            System.out.println("1. Masuk (Login)");
            System.out.println("2. Daftar Akun");
            System.out.println("0. Keluar");
            System.out.println("========================");

            int pilihan = FormHandler.integerForm("Pilih menu: ");
            switch (pilihan) {
                case 1 -> {
                    Object user = authHandler.showLoginForm();
                    if (user != null) {
                        sessionUtil.setCurrentUser(user);
                        System.out.println("\n✅ Berhasil masuk!");

                        if (sessionUtil.isOwner()) {
                            System.out.println("Halo, " + sessionUtil.getOwner().getName() + "!");
                            InterfaceUtil.pause(1000);
                            menuHandler.ownerMenu();
                        } else if (sessionUtil.isCachier()) {
                            System.out.println("Halo, " + sessionUtil.getCashier().getName() + "!");
                            InterfaceUtil.pause(1000);
                            menuHandler.cashierMenu();
                        } else {
                            System.out.println("Halo, " + sessionUtil.getPelanggan().getName() + "!");
                            InterfaceUtil.pause(1000);
                            menuHandler.pelangganMenu();
                        }
                    } else {
                        System.out.println("❌ Gagal login. Coba lagi.");
                        InterfaceUtil.pause(1000);
                    }
                }
                case 2 -> authHandler.showRegisterForm();
                case 0 -> {
                    System.out.println("Terima kasih telah menggunakan aplikasi ini!");
                    return;
                }
                default -> System.out.println("❌ Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}

