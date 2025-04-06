package services;

import models.*;
import repository.*;
import utils.*;

public class AuthService {

    private OwnerRepo ownerRepo;

    public AuthService() {
        this.ownerRepo = new OwnerRepo();
    }

    public Object authenticate(String username, String password) {

        Owner owner = ownerRepo.getOwnerByUsername(username);
        if (owner != null) {
            String hashedPassword = owner.getPassword();

            if (FormatUtil.checkPassword(password, hashedPassword)){                
                return owner;
            } else {
                System.out.println("❌ Invalid password");
                return null;
            }
            
        }

        Cashier cashier = new CashierRepo().getCashierByUsername(username);
        if (cashier != null) {
            String hashedPassword = cashier.getPassword();

            if (FormatUtil.checkPassword(password, hashedPassword)){
                return cashier;
            } else {
                System.out.println("❌ Invalid password");
                return null;
            }
        }

        Pelanggan pelanggan = new PelangganRepo().getPelangganByUsername(username);
        if (pelanggan != null) {
            String hashedPassword = pelanggan.getPassword();

            if (FormatUtil.checkPassword(password, hashedPassword)){
                return pelanggan;
            } else {
                System.out.println("❌ Invalid password");
                return null;
            }
        }

        System.out.println("❌ User not found");
        return null;
 
    }
    
}