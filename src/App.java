import model.User;
import service.AuthService;
import service.TransactionService;
import service.ProductService;

import userinterface.Auth;
import userinterface.CashierMenu;
import userinterface.OwnerMenu;
import userinterface.ProductUi;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        AuthService userService = new AuthService();
        TransactionService transactionService = new TransactionService(); 
        ProductService productService = new ProductService();
        
        Auth auth = new Auth(userService);
        Scanner scanner = new Scanner(System.in);
        ProductUi productUi = new ProductUi(productService);

        while (true) {
            clearScreen();

            System.out.println("\n==== User System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = readIntegerInput(scanner); 

            if (choice == 1) {
                auth.register(scanner);
            } 
            else if (choice == 2) {
                User user = auth.login(scanner);
                if (user != null) { 
                    if (user.getRole().equalsIgnoreCase("cashier")) {
                        CashierMenu cashierMenu = new CashierMenu(scanner, productUi, transactionService);
                        cashierMenu.show();
                    } else if (user.getRole().equalsIgnoreCase("owner")) {
                        OwnerMenu ownerMenu = new OwnerMenu(scanner, productService);
                        ownerMenu.show();
                    } else {
                        System.out.println("🛍️ Welcome to the Customer Menu!");
                    }
                } 
            } 
            else if (choice == 0) {
                System.out.println("Exiting...");
                break;
            } 
            else {
                System.out.println("❌ Invalid choice.");
            }
        }

        scanner.close(); // 🔹 Pastikan scanner hanya ditutup setelah loop selesai
    }

    private static int readIntegerInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("⚠️ Unable to clear screen.");
        }
    }
}
