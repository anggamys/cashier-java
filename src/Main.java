import model.User;
import service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n==== User System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Enter role (ADMIN/USER): ");
                String role = scanner.nextLine().toUpperCase();

                userService.registerUser(username, password, role);
            } 
            else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = userService.login(username, password);
                
                if (user != null) {
                    System.out.println("\nWelcome, " + user.getUsername() + "!");
                    if (userService.isAdmin(user)) {
                        System.out.println("🔹 You have ADMIN privileges.");
                    } else {
                        System.out.println("🔹 You are a USER.");
                    }
                }
            } 
            else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } 
            else {
                System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}
