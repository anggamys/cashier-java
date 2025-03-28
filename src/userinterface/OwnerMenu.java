package userinterface;

import service.ProductService;
import java.util.Scanner;

public class OwnerMenu {
    private final Scanner scanner;
    private final ProductService productService;
    private final ProductUi productUi;

    public OwnerMenu(Scanner scanner, ProductService productService) {
        this.scanner = scanner;
        this.productService = productService;
        this.productUi = new ProductUi(productService); 
    }

    public void show() {
        while (true) {
            clearScreen();
            System.out.println("\n==== Owner Menu ====");
            System.out.println("1. Add Product");
            System.out.println("2. Show All Products");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = readIntegerInput();
            
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> showAllProducts();
                case 0 -> {
                    System.out.println("🔚 Exiting Owner Menu...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Please enter 1, 2, or 0.");
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

        System.out.println(success ? "✅ Product added successfully!" : "❌ Failed to add product.");
        pressEnterToContinue();
    }

    private void showAllProducts() {
        productUi.showAllProducts();
        pressEnterToContinue();
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

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
