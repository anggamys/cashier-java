// package commands;

// import java.util.Scanner;

// import utils.InterfaceUtil;

// public class OwnerMenu {
//     private final Scanner scanner;
//     private final UserUi userUi;
    
//     public OwnerMenu(Scanner scanner, UserUi userUi) {
//         this.scanner = scanner;
//         this.userUi = userUi;
//     }

//     public void show() {
//         while (true) {
//             InterfaceUtil.clearScreen();

//             System.out.println("\n==== Owner Menu ====");
//             System.out.println("1. Manage Products");
//             System.out.println("2. Manage Accounts");
//             System.out.println("3. Manage Transactions");
//             System.out.println("4. View Reports");
//             System.out.println("5. Manage Users");
//             System.out.println("0. Exit");
//             int choice = FormHandler.integerUserForm(scanner, "Enter choice: ");
            
//             switch (choice) {
//                 case 1 -> userUi.show();

//                 case 0 -> {
//                     System.out.println("🔚 Exiting Owner Menu...");
//                     return;
//                 }
//                 default -> System.out.println("❌ Invalid choice! Please enter 1, 2, or 0.");
//             }
//         }
//     }
// }
