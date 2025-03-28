package userinterfaces;

import java.util.Optional;
import java.util.Scanner;

import models.Product;
import models.Transaction;
import services.ProductService;
import services.TransactionService;
import utils.Ui;

public class CashierMenu {
    private Scanner scanner;
    private TransactionService transactionService;
    private ProductUi productUi;
    private ProductService productService;

    public CashierMenu(Scanner scanner, ProductUi productUi, TransactionService transactionService) { 
        this.scanner = scanner;
        this.productUi = productUi; 
        this.transactionService = transactionService;
        this.productService = new ProductService();
    }
    

    public void show() {
        while (true) {
            Ui.clearScreen();
            
            System.out.println("\n==== Cashier Menu ====");
            System.out.println("1. Process Transaction");
            System.out.println("0. Logout");
            int choice = Form.integerUserForm(scanner, "Choose an option: ");

            switch (choice) {
                case 1 -> processTransaction();
                case 0 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("❌ Invalid choice.");

            }
        }
    }

    private void processTransaction() {
        Ui.clearScreen();
        
        productUi.showAllProducts();

        System.out.print("\nEnter customer name: ");
        String customerName = scanner.nextLine().trim();

        if (customerName.isEmpty()) {
            System.out.println("❌ Customer name cannot be empty!");
            return;
        }

        System.out.print("Enter number of items: ");
        int numItems = Form.integerUserForm(scanner, "Enter number of items: ");
        
        if (numItems <= 0) {
            System.out.println("❌ The number of items must be at least 1.");
            return;
        }

        int[] productIds = new int[numItems];
        int[] quantities = new int[numItems];

        for (int i = 0; i < numItems; i++) {
            System.out.print("Enter product ID for item " + (i + 1) + ": ");
            productIds[i] = Form.integerUserForm(scanner, "Enter product ID for item ");
            
            if (productIds[i] <= 0) {
                System.out.println("❌ Invalid product ID! Try again.");
                i--; 
                continue;
            }

            System.out.print("Enter quantity for item " + (i + 1) + ": ");
            quantities[i] = Form.integerUserForm(scanner, "Enter quantity for item ");
            
            if (quantities[i] <= 0) {
                System.out.println("❌ Quantity must be greater than 0! Try again.");
                i--; 
            }
        }

        // Confirm transaction
        System.out.println("\nConfirm Transaction:");
        System.out.println("Customer Name: " + customerName);
        for (int i = 0; i < numItems; i++) {
            System.out.println(" - Product ID: " + productIds[i] + " | Quantity: " + quantities[i]);
        }

        System.out.print("\nProceed with transaction? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("yes")) {
            System.out.println("❌ Transaction canceled.");
            return;
        }

        // Execute transaction
        Optional<Integer> transactionId = transactionService.addTransaction(customerName, productIds, quantities);
        Optional<Transaction> result = transactionService.getTransactionById(transactionId.orElse(-1));

        if (result.isPresent()) {
            Transaction transaction = result.get();
            System.out.println("\n✅ Transaction Successful!");
            System.out.println("=====================================");
            System.out.println("Transaction ID   : " + transaction.getId());
            System.out.println("Customer Name    : " + transaction.getCustomerName());
            System.out.println("Date            : " + transaction.getDate());
            System.out.println("-------------------------------------");

            System.out.println("Items Purchased:");
            for (int i = 0; i < numItems; i++) {
                Product product = productService.getProductById(productIds[i]);
                if (product != null) {
                    System.out.printf(" - %-20s (ID: %d) x %d  =  %,d\n", 
                        product.getName(), productIds[i], quantities[i], product.getPrice() * quantities[i]);
                }
            }
            System.out.println("-------------------------------------");
            System.out.printf("Total Price      : %,d\n", transaction.getTotalPrice());
            System.out.println("=====================================");
        } else {
            System.out.println("❌ Transaction failed. Please try again.");
        }

        Ui.pressEnterToContinue();
    }
}
