package services;

import models.*;
import repository.*;
import utils.*;

public class PelangganService {

    private final PelangganRepo pelangganRepo;

    public PelangganService() {
        this.pelangganRepo = new PelangganRepo();
    }

    public Pelanggan addPelanggan(String fullName, String phoneNumber, String email, String address, String username, String password) {
        String pelangganID = FormatUtil.generateUniqueID();
        String hashedPassword = FormatUtil.hashedPassword(password);

        try {
            Pelanggan newPelanggan = new Pelanggan(pelangganID, fullName, email, phoneNumber, address, username, hashedPassword);
            Pelanggan addedPelanggan = pelangganRepo.addPelanggan(newPelanggan);

            if (addedPelanggan != null) {
                return addedPelanggan;
            } else {
                System.out.println("Failed to add pelanggan.");
                return null;
            }

        } catch (Exception e) {
            FormatUtil.logError("PelangganService", "addPelanggan", e);
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
            FormatUtil.logError("PelangganService", "getPelangganByUsername", e);
            return null;
        }
    }

    public Pelanggan getPelangganById(String id) {
        try {
            Pelanggan pelanggan = pelangganRepo.getPelangganById(id);
            if (pelanggan != null) {
                System.out.println("Pelanggan found: " + pelanggan);
                return pelanggan;
            } else {
                System.out.println("Pelanggan not found.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("PelangganService", "getPelangganById", e);
            return null;
        }
    }

    public Pelanggan[] getAllPelanggan() {
        try {
            Pelanggan[] pelanggans = pelangganRepo.getAllPelanggan();
            if (pelanggans != null) {
                return pelanggans;
            } else {
                System.out.println("No pelanggan found.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("PelangganService", "getAllPelanggan", e);
            return null;
        }
    }

    public Pelanggan updatePelanggan(Pelanggan pelanggan) {
        String newPassword = FormatUtil.hashedPassword(pelanggan.getPassword());
        pelanggan.setPassword(newPassword);
        
        try {
            Pelanggan updatedPelanggan = pelangganRepo.updatePelanggan(pelanggan);
            if (updatedPelanggan != null) {
                return updatedPelanggan;
            } else {
                System.out.println("Failed to update pelanggan.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("PelangganService", "updatePelanggan", e);
            return null;
        }
    }

    public boolean deletePelanggan(String id) {
        try {
            boolean isDeleted = pelangganRepo.deletePelangganById(id);
            if (isDeleted) {
                System.out.println("Pelanggan deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete pelanggan.");
                return false;
            }
        } catch (Exception e) {
            FormatUtil.logError("PelangganService", "deletePelanggan", e);
            return false;
        }
    }
}