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

    public Owner getOwnerById(String id) {
        try {
            Owner owner = ownerRepo.getOwnerById(id);
            if (owner != null) {
                System.out.println("Owner found: " + owner);
                return owner;
            } else {
                System.out.println("Owner not found.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "getOwnerById", e);
            return null;
        }
    }

    public Owner[] getAllOwners() {
        try {
            Owner[] owners = ownerRepo.getAllOwners();
            if (owners != null) {
                return owners;
            } else {
                System.out.println("No owners found.");
                return new Owner[0];
            }
        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "getAllOwners", e);
            return new Owner[0];
        }
    }

    public Owner updateOwner(Owner owner) {
        String newPassword = FormatUtil.hashedPassword(owner.getPassword());
        owner.setPassword(newPassword);

        try {
            Owner updatedOwner = ownerRepo.updateOwner(owner);
            if (updatedOwner != null) {
                System.out.println("Owner updated successfully: " + updatedOwner);
                return updatedOwner;
            } else {
                System.out.println("Failed to update owner.");
                return null;
            }
        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "updateOwner", e);
            return null;
        }
    }

    public boolean deleteOwner(String id) {
        try {
            boolean isDeleted = ownerRepo.deleteOwnerById(id);
            if (isDeleted) {
                System.out.println("Owner deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete owner.");
                return false;
            }
        } catch (Exception e) {
            FormatUtil.logError("OwnerService", "deleteOwner", e);
            return false;
        }
    }
}
