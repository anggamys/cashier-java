package services;

import models.*;
import repository.*;
import utils.*;

public class PelangganService {

    private final PelangganRepo pelangganRepo;

    public PelangganService() {
        this.pelangganRepo = new PelangganRepo();
    }

    public Pelanggan addPelanggan(String firstName, String lastName, int phoneNumber, String email, String address, String username, String password) {
        String pelangganID = FormatUtil.generateUniqueID();
        String fullName = firstName + " " + lastName;
        String hashedPassword = FormatUtil.hashedPassword(password);

        try {
            Pelanggan newPelanggan = new Pelanggan(pelangganID, fullName, email, phoneNumber, address, username, hashedPassword);
            Pelanggan addedPelanggan = pelangganRepo.addPelanggan(newPelanggan);

            if (addedPelanggan != null) {
                System.out.println("Pelanggan added successfully: " + addedPelanggan);
                return addedPelanggan;
            } else {
                System.out.println("Failed to add pelanggan.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error adding pelanggan: " + e.getMessage());
            return null;
        }
    }

    public Pelanggan getPelangganByUsername(String username) {
        try {
            Pelanggan pelanggan = pelangganRepo.getPelangganByUsername(username);
            if (pelanggan != null) {
                System.out.println("Pelanggan found: " + pelanggan);
                return pelanggan;
            } else {
                System.out.println("Pelanggan not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving pelanggan: " + e.getMessage());
            return null;
        }
    }
}