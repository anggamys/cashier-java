package utils;

import models.*;

public class SessionUtil {

    private static SessionUtil instance;
    private Object currentUser;

    private SessionUtil() {}

    public static SessionUtil getInstance() {
        if (instance == null) {
            instance = new SessionUtil();
        }
        return instance;
    }

    public void setCurrentUser(Object user) {
        this.currentUser = user;
    }

    public Object getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }

    public boolean isOwner() {
        return currentUser instanceof Owner;
    }

    public boolean isCashier() {
        return currentUser instanceof Cashier;
    }

    public boolean isPelanggan() {
        return currentUser instanceof Pelanggan;
    }

    public Owner getOwner() {
        return isOwner() ? (Owner) currentUser : null;
    }

    public Cashier getCashier() {
        return isCashier() ? (Cashier) currentUser : null;
    }

    public Pelanggan getPelanggan() {
        return isPelanggan() ? (Pelanggan) currentUser : null;
    }
}
