package userinterface;

import java.util.Scanner;

public class CashierMenu {
    private Scanner scanner;

    public CashierMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        while (true) {
            clearScreen(); 
            
            System.out.println("\n==== Cashier Menu ====");
            System.out.println("1. Add product");
            System.out.println("2. View product");
            System.out.println("3. Add customer");
            System.out.println("4. View customer");
            System.out.println("0. Logout");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            if (choice == 1) {
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                System.out.println("✅ Product added: " + productName);
            } 
            else if (choice == 2) {
                System.out.println("📦 Viewing all products...");
            } 
            else if (choice == 3) {
                System.out.print("Enter customer name: ");
                String customerName = scanner.nextLine();
                System.out.println("✅ Customer added: " + customerName);
            } 
            else if (choice == 4) {
                System.out.println("👥 Viewing all customers...");
            } 
            else if (choice == 0) {
                System.out.println("🔴 Logging out...");
                break;
            } 
            else {
                System.out.println("❌ Invalid choice.");
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
}
