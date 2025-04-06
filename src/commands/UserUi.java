// package commands;

// import java.util.Scanner;

// public class UserUi {
//     private final Scanner scanner;

//     public UserUi(Scanner scanner) {
//         this.scanner = scanner;
//     }

//     public void show() {
//         while (true) {
//             System.out.println("\n==== User Menu ====");

//             System.out.println("1. View all users");
//             System.out.println("0. Exit");
//             int choice = FormHandler.integerUserForm(scanner, "Enter choice: ");

//             switch (choice) {
//                 case 1 -> viewAllUsers();
//                 case 0 -> {
//                     System.out.println("🔚 Exiting User Menu...");
//                     return;
//                 }
//                 default -> System.out.println("❌ Invalid choice! Please enter 1 or 0.");
//             }
//         }
//     }

//     private void viewAllUsers() {
        
//     }
// }
