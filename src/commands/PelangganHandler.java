package commands;

import models.*;
import services.*;
import utils.*;

public class PelangganHandler {

    private final PelangganService pelangganService;

    public PelangganHandler() {
        this.pelangganService = new PelangganService();
    }

    public void addPelanggan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN PELANGGAN ===");

        String firstName = FormHandler.stringForm("Nama depan: ");
        String lastName = FormHandler.stringForm("Nama belakang: ");
        int phoneNumber = FormHandler.integerForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");

        Pelanggan newPelanggan = pelangganService.addPelanggan(
            firstName, lastName, phoneNumber, email, address, username, password
        );

        if (newPelanggan != null) {
            System.out.println("✅ Pelanggan berhasil didaftarkan: " + newPelanggan.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan pelanggan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }
}