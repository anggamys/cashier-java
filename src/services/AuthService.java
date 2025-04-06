package services;

import models.*;
import repository.*;
import utils.*;

public class AuthService {

    private final OwnerRepo ownerRepo;
    private final CashierRepo cashierRepo;
    private final PelangganRepo pelangganRepo;

    public AuthService() {
        this.ownerRepo = new OwnerRepo();
        this.cashierRepo = new CashierRepo();
        this.pelangganRepo = new PelangganRepo();
    }

    public Object authenticate(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Username dan password tidak boleh kosong");
            return null;
        }

        // Coba autentikasi sebagai Owner
        Owner owner = ownerRepo.getOwnerByUsername(username);
        if (owner != null && isPasswordValid(password, owner.getPassword())) {
            return owner;
        }

        // Coba autentikasi sebagai Cashier
        Cashier cashier = cashierRepo.getCashierByUsername(username);
        if (cashier != null && isPasswordValid(password, cashier.getPassword())) {
            return cashier;
        }

        // Coba autentikasi sebagai Pelanggan
        Pelanggan pelanggan = pelangganRepo.getPelangganByUsername(username);
        if (pelanggan != null && isPasswordValid(password, pelanggan.getPassword())) {
            return pelanggan;
        }

        System.out.println("❌ Username atau password salah");
        return null;
    }

    private boolean isPasswordValid(String inputPassword, String hashedPassword) {
        boolean valid = FormatUtil.checkPassword(inputPassword, hashedPassword);
        if (!valid) {
            System.out.println("❌ Invalid password");
        }
        return valid;
    }
}
