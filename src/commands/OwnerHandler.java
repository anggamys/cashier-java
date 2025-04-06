package commands;

import services.*;
import models.*;
import utils.*;

public class OwnerHandler {

    private final OwnerService ownerService;

    public OwnerHandler() {
        this.ownerService = new OwnerService();
    }

    public void addOwner() {
        InterfaceUtil.clearScreen();
        System.out.println("=== PENDAFTARAN PEMILIK ===");

        String firstName = FormHandler.stringForm("Nama depan: ");
        String lastName = FormHandler.stringForm("Nama belakang: ");
        String phoneNumber = FormHandler.phoneForm("Nomor HP: ");
        String email = FormHandler.emailForm("Email: ");
        String address = FormHandler.stringForm("Alamat: ");
        String username = FormHandler.stringForm("Username: ");
        String password = FormHandler.stringForm("Password: ");

        int phoneNumberInt = FormatUtil.parseStrToInt(phoneNumber);

        Owner newOwner = ownerService.addOwner(
            firstName, lastName, phoneNumberInt, email, address, username, password
        );

        if (newOwner != null) {
            System.out.println("✅ Pemilik berhasil didaftarkan: " + newOwner.getName());
        } else {
            System.out.println("❌ Gagal mendaftarkan pemilik.");
        }

        InterfaceUtil.pressEnterToContinue();
    }
}