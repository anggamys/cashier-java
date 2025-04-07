package commands;

import models.*;
import services.*;
import utils.*;

public class PelangganHandler {

    private final PelangganService pelangganService;
    private final SessionUtil sessionUtil;

    public PelangganHandler() {
        this.pelangganService = new PelangganService();
        this.sessionUtil = SessionUtil.getInstance();
    }

    public void addPelanggan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN PELANGGAN ===");

        String fullName = FormHandler.stringForm("Nama Lengkap: ");
        int phoneNumber = FormHandler.integerForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");

        Pelanggan newPelanggan = pelangganService.addPelanggan(
            fullName, phoneNumber, email, address, username, password
        );

        if (newPelanggan != null) {
            System.out.println("✅ Pelanggan berhasil didaftarkan: " + newPelanggan.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan pelanggan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void lihatSemuaPelanggan() {
        InterfaceUtil.clearScreen();
        System.out.println("=== DAFTAR PELANGGAN ===");

        Pelanggan[] pelangganList = pelangganService.getAllPelanggan();

        if (pelangganList.length == 0) {
            System.out.println("❌ Tidak ada pelanggan yang terdaftar.");
        } else {
            for (Pelanggan pelanggan : pelangganList) {
                System.out.println(pelanggan);
            }
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void updatePelangganById() {
        InterfaceUtil.clearScreen();
        System.out.println("=== UPDATE PELANGGAN ===");

        Pelanggan currentPelanggan = sessionUtil.getPelanggan();

        Pelanggan pelanggan = pelangganService.getPelangganById(currentPelanggan.getId());

        String fullName = FormHandler.stringForm("Nama Lengkap (" + pelanggan.getName() + "): ");
        int phoneNumber = FormHandler.integerForm("Nomor HP (" + pelanggan.getPhoneNumber() + "): ");
        String email = FormHandler.emailForm("Email (" + pelanggan.getEmail() + "): ");
        String address = FormHandler.stringForm("Alamat (" + pelanggan.getAddress() + "): ");
        String username = FormHandler.stringForm("Username (" + pelanggan.getUsername() + "): ");
        String password = FormHandler.stringForm("Password (" + pelanggan.getPassword() + "): ");
        String confirmPassword = FormHandler.stringForm("Konfirmasi Password: ");
        if (!password.equals(confirmPassword)) {
            System.out.println("❌ Password tidak cocok.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }

        // Check if username already exists
        Pelanggan existingPelanggan = pelangganService.getPelangganByUsername(username);
        if (existingPelanggan != null && !existingPelanggan.getId().equals(pelanggan.getId())) {
            System.out.println("❌ Username sudah terdaftar.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }
        // Update the pelanggan
        Pelanggan updatedPelanggan = pelangganService.updatePelanggan(
            new Pelanggan(pelanggan.getId(), fullName, email, phoneNumber, address, username, password)
        );

        if (updatedPelanggan == null) {
            System.out.println("❌ Gagal memperbarui pelanggan.");
            InterfaceUtil.pressEnterToContinue();
            return;
        }
        // Update the session
        sessionUtil.setCurrentUser(updatedPelanggan);

        System.out.println("✅ Pelanggan berhasil diperbarui.");

        InterfaceUtil.pressEnterToContinue();
    }
}