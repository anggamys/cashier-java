package userinterfaces;

import java.util.Scanner;

import services.ProductService;

import utils.Ui;

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
            Ui.clearScreen();

            System.out.println("\n==== Owner Menu ====");
            System.out.println("1. Add Product");
            System.out.println("2. Show All Products");
            System.out.println("0. Exit");
            int choice = Form.integerUserForm(scanner, "Enter choice: ");
            
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
        Ui.clearScreen();
    
        String productName, productCategory;
        int productPrice, productStock;
    
        System.out.println("\n==== Add Product ====");
    
        productName = Form.stringUserForm(scanner, "Enter product name: ");
        productPrice = Form.integerUserForm(scanner, "Enter product price: ");
        productStock = Form.integerUserForm(scanner, "Enter product stock: ");
        productCategory = Form.stringUserForm(scanner, "Enter product category: ");

        boolean success = productService.addProduct(productName.toString(), productCategory.toString(), productPrice, productStock);
    
        System.out.println(success ? "✅ Product added successfully!" : "❌ Failed to add product.");
        Ui.pressEnterToContinue();
    }    

    private void showAllProducts() {
        Ui.clearScreen();
        productUi.showAllProducts();
        Ui.pressEnterToContinue();
    }
}
