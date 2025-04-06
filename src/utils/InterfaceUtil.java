package utils;

import java.io.IOException;

public class InterfaceUtil {

    public static void clearScreen() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO()
                    .start()
                    .waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("⚠️ Unable to clear screen.");
        }
    }

    public static void pressEnterToContinue() {
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
            System.out.println("⚠️ Unable to read input.");
        }
    }

    public static void pause(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}