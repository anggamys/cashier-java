package userinterface;

import model.User;
import service.AuthService;
import java.io.IOException;
import java.util.Scanner;

public class Auth {
    private AuthService userService;

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
        } catch (IOException | InterruptedException ex) {
            System.out.println("❌ Failed to clear screen!");
        }
    }

    public void register(Scanner scanner) {
        clearScreen(); // 🔹 Bersihkan layar sebelum menampilkan menu
        System.out.println("\n===== REGISTER =====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

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

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        userService.registerUser(username, password, role.toLowerCase(), name, email, phone, address);
        System.out.println("\n✅ Registration successful!");
    }

    public User login(Scanner scanner) {
        clearScreen(); // 🔹 Bersihkan layar sebelum menampilkan menu login
        System.out.println("\n===== LOGIN =====");

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // Validasi input kosong
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
