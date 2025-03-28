package userinterface;

import java.util.Scanner;
import service.*;

public class OwnerMenu {
    private Scanner scanner;
    private ProductService productService;

    public OwnerMenu(Scanner scanner) {
        this.scanner = scanner;
        this.productService = new ProductService();
    }

    public void show() {
        while (true) {
            clearScreen();
            System.out.println("\n==== Owner Menu ====");
            System.out.println("1. Add Product");
            System.out.println("2. Show All Product");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    ProductUi productUi = new ProductUi(productService);
                    productUi.showAllProducts();

                    System.out.print("\nPress Enter to continue...");
                    scanner.nextLine();
                break;
                case 0:
                    System.out.println("🔚 Exiting Owner Menu...");
                    return;
                default:
                    System.out.println("❌ Invalid choice! Please enter 1 or 2.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.print("Enter product price: ");
        int productPrice = readIntegerInput();

        System.out.print("Enter product stock: ");
        int productStock = readIntegerInput();

        System.out.print("Enter product category: ");
        String productCategory = scanner.nextLine();

        boolean success = productService.addProduct(productName, productCategory, productPrice, productStock);

        if (success) {
            System.out.println("✅ Product added successfully!");
        } else {
            System.out.println("❌ Failed to add product.");
        }

        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private int readIntegerInput() {
        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (Exception e) {
                System.out.print("❌ Invalid input! Please enter a valid number: ");
                scanner.nextLine();
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }
}
