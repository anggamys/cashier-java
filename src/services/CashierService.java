package services;

import models.*;
import repository.*;
import utils.*;

public class CashierService {

    private final CashierRepo cashierRepo;

    public CashierService() {
        this.cashierRepo = new CashierRepo();
    }

    public Cashier addCashier(String fullName, int phoneNumber, String email, String address, String username, String password) {
        String cashierID = FormatUtil.generateUniqueID();
        String hashedPassword = FormatUtil.hashedPassword(password);

        try {
            Cashier newCashier = new Cashier(cashierID, fullName, email, phoneNumber, address, username, hashedPassword);
            Cashier addedCashier = cashierRepo.addCashier(newCashier);

            if (addedCashier != null) {
                return addedCashier;
            } else {
                System.out.println("Failed to add cashier.");
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error adding cashier: " + e.getMessage());
            return null;
        }
    }
    
    public Cashier getCashierByUsername(String username) {
        try {
            Cashier cashier = cashierRepo.getCashierByUsername(username);
            if (cashier != null) {
                return cashier;
            } else {
                System.out.println("Cashier not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving cashier: " + e.getMessage());
            return null;
        }
    }

    public Cashier getCashierById(String id) {
        try {
            Cashier cashier = cashierRepo.getCashierById(id);
            if (cashier != null) {
                return cashier;
            } else {
                System.out.println("Cashier not found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving cashier: " + e.getMessage());
            return null;
        }
    }

    public Cashier[] getAllCashiers() {
        try {
            Cashier[] cashiers = cashierRepo.getAllCashiers();
            if (cashiers != null) {
                return cashiers;
            } else {
                System.out.println("No cashiers found.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error retrieving cashiers: " + e.getMessage());
            return null;
        }
    }

    public Cashier updateCashier(Cashier cashier) {
        String newPassword = FormatUtil.hashedPassword(cashier.getPassword());
        cashier.setPassword(newPassword);

        try {
            Cashier updatedCashier = cashierRepo.updateCashier(cashier);
            if (updatedCashier != null) {
                return updatedCashier;
            } else {
                System.out.println("Failed to update cashier.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error updating cashier: " + e.getMessage());
            return null;
        }
    }

    public boolean deleteCashier(String id) {
        try {
            return cashierRepo.deleteCashier(id);
        } catch (Exception e) {
            System.out.println("Error deleting cashier: " + e.getMessage());
            return false;
        }
    }
}