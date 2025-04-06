package commands;

import models.*;
import utils.*;
import services.*;

public class CashierHandler {

    private final CashierService cashierService;

    public CashierHandler() {
        this.cashierService = new CashierService();
    }

    public void addCashier() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN KASIR ===");

        String firstName = FormHandler.stringForm("Nama depan: ");
        String lastName = FormHandler.stringForm("Nama belakang: ");
        int phoneNumber = FormHandler.integerForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");

        Cashier newCashier = cashierService.addCashier(
            firstName, lastName, phoneNumber, email, address, username, password
        );

        if (newCashier != null) {
            System.out.println("✅ Kasir berhasil didaftarkan: " + newCashier.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan kasir.");
        }

        InterfaceUtil.pressEnterToContinue();
    }
}
