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
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 
            
            if (choice == 1) {
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
            } 
            else if (choice == 2) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user = userService.login(username, password);
                
                if (user != null) {
                    System.out.println("\nWelcome, " + user.getUsername() + "!");
                    if (userService.isCashier(user)) {
                        System.out.println("🔹 You are a CASHIER.");
                    } else if (userService.isOwner(user)) {
                        System.out.println("🔹 You are an OWNER.");
                    } else {
                        System.out.println("🔹 You are a CUSTOMER.");
                    }
                }
            } 
            else if (choice == 0) {
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
