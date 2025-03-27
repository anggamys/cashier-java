package userinterface;

import model.User;
import service.UserService;
import java.util.Scanner;

public class Auth {
    private UserService userService;

    public Auth(UserService userService) {
        this.userService = userService;
    }

    public void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String role;
        while (true) {
            System.out.print("Enter role (CASHIER/CUSTOMER/OWNER): ");
            role = scanner.nextLine().toLowerCase();
            if (role.equals("cashier") || role.equals("customer") || role.equals("owner")) {
                break;
            }
            System.out.println("❌ Invalid role! Please enter CASHIER, CUSTOMER, or OWNER.");
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        userService.registerUser(username, password, role, name, email, phone, address);
        System.out.println("✅ Registration successful!");
    }

    public User login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);

        if (user != null) {
            System.out.println("\n✅ Welcome, " + user.getUsername() + "!");
            return user;
        } else {
            System.out.println("❌ Invalid username or password!");
            return null;
        }
    }
}
