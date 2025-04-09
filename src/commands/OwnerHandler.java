package commands;

import services.*;
import models.*;
import utils.*;

public class OwnerHandler {

    private final OwnerService ownerService;
    private final SessionUtil sessionUtil;

    public OwnerHandler() {
        this.ownerService = new OwnerService();
        this.sessionUtil = SessionUtil.getInstance();
    }

    public void addOwner() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN PEMILIK ===");

        String fullName = FormHandler.stringForm("Nama Lengkap: ");
        String phoneNumber = FormHandler.phoneForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");

        Owner newOwner = ownerService.addOwner(
            fullName, phoneNumber, email, address, username, password
        );

        if (newOwner != null) {
            System.out.println("✅ Pemilik berhasil didaftarkan: " + newOwner.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan pemilik.");
        }

        InterfaceUtil.pressEnterToContinue();
    }

    public void detailOwner() {
        InterfaceUtil.clearScreen();

        System.out.println("=== DETAIL PEMILIK ===");

        Owner currentOwner = sessionUtil.getOwner();
        
        if (currentOwner != null) {
            System.out.println("Nama Lengkap: " + currentOwner.getName());
            System.out.println("Nomor HP: " + currentOwner.getPhoneNumber());
            System.out.println("Email: " + currentOwner.getEmail());
            System.out.println("Alamat: " + currentOwner.getAddress());
            System.out.println("Username: " + currentOwner.getUsername());
        } else {
            System.out.println("❌ Pemilik tidak ditemukan.");
        }

        InterfaceUtil.pressEnterToContinue();
    }
    
    public void updateOwnerById() {
        InterfaceUtil.clearScreen();

        System.out.println("=== UPDATE PEMILIK ===");

        Owner currentOwner = sessionUtil.getOwner();

        Owner owner = ownerService.getOwnerById(currentOwner.getId());

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

        Owner updatedOwner = ownerService.updateOwner(
            new Owner(owner.getId(), fullName, email, phoneNumber, address, username, password)
        );

        if (updatedOwner != null) {
            System.out.println("✅ Pemilik berhasil diperbarui: " + updatedOwner.getName());
        } else {
            System.out.println("❌ Gagal memperbarui pemilik.");
        }
        InterfaceUtil.pressEnterToContinue();
    }
}