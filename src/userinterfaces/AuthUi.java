package userinterfaces;

import models.User;
import utils.Ui;
import services.AuthService;

import java.util.Scanner;

public class AuthUi {
    private final AuthService userService; 

    public AuthUi(AuthService userService) {
        this.userService = userService;
    }

    public void register(Scanner scanner) {
        Ui.clearScreen();
        System.out.println("\n===== REGISTER =====");

        String username, password, role, name, email, phone, address;

        username = Form.stringUserForm(scanner, "Enter username: ");
        password = Form.stringUserForm(scanner, "Enter password: ");
        role = Form.stringUserForm(scanner, "Enter role (cashier, customer, owner): ");
        name = Form.stringUserForm(scanner, "Enter name: ");
        email = Form.emailUserForm(scanner, "Enter email: ");
        phone = Form.phoneUserForm(scanner, "Enter phone: ");
        address = Form.stringUserForm(scanner, "Enter address: ");

        userService.registerUser(username, password, role, name, email, phone, address);
        System.out.println("\n✅ Registration successful!");

        Ui.pressEnterToContinue();
    }

    public User login(Scanner scanner) {
        Ui.clearScreen();
        System.out.println("\n===== LOGIN =====");
    
        String username = Form.stringUserForm(scanner, "Enter username: ");
        String password = Form.stringUserForm(scanner, "Enter password: ");
    
        User user = userService.login(username, password);
    
        if (user != null) {
            System.out.println("\n✅ Welcome, " + user.getUsername() + " (" + user.getRole().toUpperCase() + ")!");
            return user;
        }
    
        System.out.println("❌ Invalid username or password!");
        Ui.pressEnterToContinue();
        return null;
    }
}
