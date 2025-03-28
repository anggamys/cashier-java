package userinterfaces;

import java.util.Scanner;
import utils.Io;

public class Form {
    public static String stringUserForm(Scanner scanner, String message) {
        System.out.print(message);
        return Io.stringInput(scanner);
    }

    public static int integerUserForm(Scanner scanner, String message) {
        System.out.print(message);
        return Io.integerInput(scanner);
    }

    public static String emailUserForm(Scanner scanner, String message) {
        System.out.print(message);
        return Io.emailInput(scanner);
    }

    public static String phoneUserForm(Scanner scanner, String message) {
        System.out.print(message);
        return Io.phoneInput(scanner);
    }

    public static String roleUserForm(Scanner scanner, String message) {
        System.out.print(message);
        return Io.roleInput(scanner);
    }
}
