package commands;

import services.*;
import utils.*;

public class AuthHandler {

    private final AuthService authService;
    private final SessionUtil sessionUtil;
    private final CashierHandler cashierHandler;
    private final OwnerHandler ownerHandler;
    private final PelangganHandler pelangganHandler;

    public AuthHandler() {
        this.authService = new AuthService();
        this.cashierHandler = new CashierHandler();
        this.ownerHandler = new OwnerHandler();
        this.pelangganHandler = new PelangganHandler();

        this.sessionUtil = SessionUtil.getInstance();
    }

    public Object showLoginForm() {
        InterfaceUtil.clearScreen();
        System.out.println("======= LOGIN =======");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");
        return authService.authenticate(username, password);
    }

    public void showRegisterForm() {
        InterfaceUtil.clearScreen();
        System.out.println("==== DAFTAR AKUN ====");
        System.out.println("Pilih jenis akun yang ingin didaftarkan:");
        System.out.println("1. Owner");
        System.out.println("2. Kasir");
        System.out.println("3. Pelanggan");
        System.out.println("0. Kembali ke menu utama");

        int pilihan = FormHandler.integerForm("Pilihan Anda: ");
        switch (pilihan) {
            case 1 -> ownerHandler.addOwner();
            case 2 -> cashierHandler.addCashier();
            case 3 -> pelangganHandler.addPelanggan();
            case 0 -> System.out.println("Kembali ke menu utama...");
            default -> {
                System.out.println("❌ Pilihan tidak valid. Coba lagi.");
                InterfaceUtil.pause(1000);
                showRegisterForm();
            }
        }
    }

    public void logout() {
        sessionUtil.logout();
        System.out.println("✅ Berhasil logout.");
        InterfaceUtil.pause(1000);
    }
}

