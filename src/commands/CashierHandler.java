package commands;

import models.*;
import utils.*;
import services.*;

public class CashierHandler {

    private final CashierService cashierService;
    private final SessionUtil sessionUtil;

    public CashierHandler() {
        this.cashierService = new CashierService();
        this.sessionUtil = SessionUtil.getInstance();
    }

    public void addCashier() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN KASIR ===");

        String fullName = FormHandler.stringForm("Nama Lengkap: ");
        String phoneNumber = FormHandler.phoneForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");
        String confirmPassword = FormHandler.stringForm("Konfirmasi Password: ");

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Password tidak cocok.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        // Check if username already exists
        if (cashierService.getCashierByUsername(username) != null) {
            System.out.println("❌ Username sudah terdaftar.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }
        
        Cashier newCashier = cashierService.addCashier(
            fullName, phoneNumber, email, address, username, password
        );

        if (newCashier != null) {
            System.out.println("✅ Kasir berhasil didaftarkan: " + newCashier.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan kasir.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void lihatSemuaKasir() {
        InterfaceUtil.clearScreen();
        System.out.println("=== DAFTAR KASIR ===");

        Cashier[] cashiers = cashierService.getAllCashiers();

        if (cashiers == null || cashiers.length == 0) {
            System.out.println("❌ Tidak ada kasir yang terdaftar.");
        } else {
            for (Cashier cashier : cashiers) {
                System.out.println(cashier);
            }
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void updateCashier() {
        InterfaceUtil.clearScreen();

        lihatSemuaKasir();

        System.out.println("=== UPDATE KASIR ===");

        String cashierId = FormHandler.stringForm("ID Kasir yang ingin diupdate: ");
        Cashier cashier = cashierService.getCashierById(cashierId);

        if (cashier == null) {
            System.out.println("❌ Kasir tidak ditemukan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        String fullName = FormHandler.stringForm("Nama Lengkap (" + cashier.getName() + "): ");
        String phoneNumber = FormHandler.phoneForm("Nomor HP (" + cashier.getPhoneNumber() + "): ");
        String email = FormHandler.emailForm("Email (" + cashier.getEmail() + "): ");
        String address = FormHandler.stringForm("Alamat (" + cashier.getAddress() + "): ");
        String username = FormHandler.stringForm("Username (" + cashier.getUsername() + "): ");
        String password = FormHandler.stringForm("Password (" + cashier.getPassword() + "): ");
        String confirmPassword = FormHandler.stringForm("Konfirmasi Password: ");

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Password tidak cocok.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        // Check if username already exists
        if (!cashier.getUsername().equals(username) && cashierService.getCashierByUsername(username) != null) {
            System.out.println("❌ Username sudah terdaftar.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Cashier updatedCashier = cashierService.updateCashier(
            new Cashier(cashier.getId(), fullName, email, phoneNumber, address, username, password)
        );

        if (updatedCashier != null) {
            System.out.println("✅ Kasir berhasil diupdate: " + updatedCashier.getName());
        } else {
            System.out.println("❌ Gagal mengupdate kasir.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void updateCashierById(){
        InterfaceUtil.clearScreen();

        Cashier currentCashier = sessionUtil.getCashier();

        Cashier cashier = cashierService.getCashierById(currentCashier.getId());

        if (cashier == null) {
            System.out.println("❌ Kasir tidak ditemukan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        String fullName = FormHandler.stringForm("Nama Lengkap (" + cashier.getName() + "): ");
        String phoneNumber = FormHandler.phoneForm("Nomor HP (" + cashier.getPhoneNumber() + "): ");
        String email = FormHandler.emailForm("Email (" + cashier.getEmail() + "): ");
        String address = FormHandler.stringForm("Alamat (" + cashier.getAddress() + "): ");
        String username = FormHandler.stringForm("Username (" + cashier.getUsername() + "): ");
        String password = FormHandler.stringForm("Password (" + cashier.getPassword() + "): ");
        String confirmPassword = FormHandler.stringForm("Konfirmasi Password: ");

        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Password tidak cocok.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        // Check if username already exists
        if (!cashier.getUsername().equals(username) && cashierService.getCashierByUsername(username) != null) {
            System.out.println("❌ Username sudah terdaftar.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Cashier updatedCashier = cashierService.updateCashier(
            new Cashier(cashier.getId(), fullName, email, phoneNumber, address, username, password)
        );

        if (updatedCashier != null) {
            System.out.println("✅ Kasir berhasil diupdate: " + updatedCashier.getName());
        } else {
            System.out.println("❌ Gagal mengupdate kasir.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void deleteCashier() {
        InterfaceUtil.clearScreen();

        lihatSemuaKasir();

        System.out.println("=== HAPUS KASIR ===");

        String cashierId = FormHandler.stringForm("ID Kasir yang ingin dihapus: ");
        Cashier cashier = cashierService.getCashierById(cashierId);

        if (cashier == null) {
            System.out.println("❌ Kasir tidak ditemukan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        Boolean isConfirmed = FormHandler.confirmationForm("Apakah Anda yakin ingin menghapus kasir " + cashier.getName() + "? (y/n): ");
        if (!isConfirmed) {
            System.out.println("❌ Penghapusan kasir dibatalkan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        boolean isDeleted = cashierService.deleteCashier(cashierId);

        if (isDeleted) {
            System.out.println("✅ Kasir berhasil dihapus: " + cashier.getName());
        } else {
            System.out.println("❌ Gagal menghapus kasir.");
        }

        InterfaceUtil.pressEnterToContinue();
    }
}
