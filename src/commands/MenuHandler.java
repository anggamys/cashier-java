package commands;

import utils.*;

public class MenuHandler {

    private final AuthHandler authHandler;

    public MenuHandler() {
        this.authHandler = new AuthHandler();
    }

    public void ownerMenu() {
        InterfaceUtil.clearScreen();
        System.out.println("===== MENU OWNER =====");
        System.out.println("0. Logout");
        int pilihan = FormHandler.integerForm("Pilih menu: ");
        if (pilihan == 0) authHandler.logout();
        else System.out.println("❌ Pilihan tidak valid.");
    }

    public void cashierMenu() {
        InterfaceUtil.clearScreen();
        System.out.println("===== MENU KASIR =====");
        System.out.println("0. Logout");
        int pilihan = FormHandler.integerForm("Pilih menu: ");
        if (pilihan == 0) authHandler.logout();
        else System.out.println("❌ Pilihan tidak valid.");
    }

    public void pelangganMenu() {
        InterfaceUtil.clearScreen();
        System.out.println("===== MENU PELANGGAN =====");
        System.out.println("0. Logout");
        int pilihan = FormHandler.integerForm("Pilih menu: ");
        if (pilihan == 0) authHandler.logout();
        else System.out.println("❌ Pilihan tidak valid.");
    }
}

