package services;

import models.*;
import repository.*;
import utils.*;

public class CashierService {

    private final CashierRepo cashierRepo;

    public CashierService() {
        this.cashierRepo = new CashierRepo();
    }

    public Cashier addCashier(String firstName, String lastName, int phoneNumber, String email, String address, String username, String password) {
        String cashierID = FormatUtil.generateUniqueID();
        String fullName = firstName + " " + lastName;
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
}