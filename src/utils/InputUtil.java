package utils;

import java.util.Scanner;

public class InputUtil {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine() {
        return scanner.nextLine();
    }

    public static void closeScanner() {
        scanner.close();
    }

    public static int readInt() {
        return scanner.nextInt();
    }
}