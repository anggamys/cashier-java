package services;

import models.*;
import repository.*;
import utils.*;

public class OwnerService {

    private final OwnerRepo ownerRepo;

    public OwnerService() {
        this.ownerRepo = new OwnerRepo();
    }

    public Owner addOwner(String firstName, String lastName, int phoneNumber, String email, String address, String username, String password) {
        String ownerID = FormatUtil.generateUniqueID();
        String fullName = firstName + " " + lastName;
        String hashedPassword = FormatUtil.hashedPassword(password);

        try {
            Owner newOwner = new Owner(ownerID, fullName, email, phoneNumber, address, username, hashedPassword);
            Owner addedOwner = ownerRepo.addOwner(newOwner);

            if (addedOwner != null) {
                System.out.println("Owner added successfully: " + addedOwner);
                return addedOwner;
            } else {
                System.out.println("Failed to add owner.");
                return null;
            }

        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "addOwner", e);
            return null;
        }
    }

    public Owner getOwnerByUsername(String username) {
        try {
            Owner owner = ownerRepo.getOwnerByUsername(username);
            if (owner != null) {
                System.out.println("Owner found: " + owner);
                return owner;
            } else {
                System.out.println("Owner not found.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "getOwnerByUsername", e);
            return null;
        }
    }
}
