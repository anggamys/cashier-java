package utils;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Io {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{8,15}$");
    private static final Set<String> VALID_ROLES = Set.of("cashier", "customer", "owner");

    public static String stringInput(Scanner scanner) {
        return scanner.nextLine().trim();
    }

    public static int integerInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid integer.");
            }
        }
    }

    public static String emailInput(Scanner scanner) {
        while (true) {
            String email = scanner.nextLine().trim();
            if (EMAIL_PATTERN.matcher(email).matches()) {
                return email;
            }
            System.out.println("❌ Invalid email format! Please enter a valid email.");
        }
    }

    public static String phoneInput(Scanner scanner) {
        while (true) {
            String phone = scanner.nextLine().trim();
            if (PHONE_PATTERN.matcher(phone).matches()) {
                return phone;
            }
            System.out.println("❌ Invalid phone number! Please enter 8-15 digit numbers.");
        }
    }

    public static String roleInput(Scanner scanner) {
        while (true) {
            String role = scanner.nextLine().trim().toLowerCase();
            if (VALID_ROLES.contains(role)) {
                return role;
            }
            System.out.println("❌ Invalid role! Please enter (cashier, customer, owner).");
        }
    }
}
