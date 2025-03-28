package userinterface;

import model.User;
import service.AuthService;
import java.util.Scanner;

public class Auth {
    private final AuthService userService; 
    
    public Auth(AuthService userService) {
        this.userService = userService;
    }

    private void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception ex) {
            System.out.println("⚠️ Unable to clear screen.");
        }
    }

    public void register(Scanner scanner) {
        clearScreen();
        System.out.println("\n===== REGISTER =====");

        String username, password, email, phone;
        
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();
            if (!username.isEmpty()) break;
            System.out.println("❌ Username cannot be empty!");
        }

        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();
            if (!password.isEmpty()) break;
            System.out.println("❌ Password cannot be empty!");
        }

        String role;
        while (true) {
            System.out.print("Enter role (CASHIER / CUSTOMER / OWNER): ");
            role = scanner.nextLine().trim();
            if (role.equalsIgnoreCase("cashier") || role.equalsIgnoreCase("customer") || role.equalsIgnoreCase("owner")) {
                break;
            }
            System.out.println("❌ Invalid role! Please enter CASHIER, CUSTOMER, or OWNER.");
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
            if (!email.isEmpty()) break;
            System.out.println("❌ Email cannot be empty!");
        }

        while (true) {
            System.out.print("Enter phone: ");
            phone = scanner.nextLine().trim();
            if (!phone.isEmpty()) break;
            System.out.println("❌ Phone number cannot be empty!");
        }

        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        userService.registerUser(username, password, role.toLowerCase(), name, email, phone, address);
        System.out.println("\n✅ Registration successful!");
    }

    public User login(Scanner scanner) {
        clearScreen();
        System.out.println("\n===== LOGIN =====");
    
        System.out.print("Enter username: ");
        String username = scanner.next(); 
        scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Username and password cannot be empty!");
            return null;
        }
    
        User user = userService.login(username, password);
    
        if (user != null) {
            System.out.println("\n✅ Welcome, " + user.getUsername() + " (" + user.getRole().toUpperCase() + ")!");
            return user;
        } else {
            System.out.println("❌ Invalid username or password!");
            return null;
        }
    }
}
