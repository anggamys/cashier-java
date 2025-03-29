import models.User;
import utils.Ui;
import services.AuthService;
import services.ProductService;
import services.TransactionService;
import services.UserService;
import userinterfaces.AuthUi;
import userinterfaces.CashierMenu;
import userinterfaces.Form;
import userinterfaces.OwnerMenu;
import userinterfaces.ProductUi;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // Inisialisasi services
        AuthService authService = new AuthService();
        TransactionService transactionService = new TransactionService();
        ProductService productService = new ProductService();
        UserService userService = new UserService();

        // Inisialisasi scanner & UI
        Scanner scanner = new Scanner(System.in);
        AuthUi authUi = new AuthUi(authService);
        ProductUi productUi = new ProductUi(productService);

        while (true) {
            Ui.clearScreen();
            System.out.println("\n==== Cashier System ====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("0. Exit");

            int choice = Form.integerUserForm(scanner, "Enter choice: ");

            switch (choice) {
                case 1 -> authUi.register(scanner);
                case 2 -> {
                    User user = authUi.login(scanner);
                    if (user != null) {
                        switch (user.getRole().toLowerCase()) {
                            case "cashier" -> new CashierMenu(scanner, productUi, transactionService).show();
                            case "owner" -> new OwnerMenu(scanner, productService, userService).show();
                            default -> System.out.println("🛍️ Welcome to the Customer Menu!");
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
}
