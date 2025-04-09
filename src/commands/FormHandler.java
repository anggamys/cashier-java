package commands;

import utils.InputUtil;

public class FormHandler {

    public static String stringForm(String message) {
        while (true) {
            System.out.printf(message);
            String input = InputUtil.readLine().trim();
            if (!input.isEmpty()) return input;

            System.out.println("\n❌ Input tidak boleh kosong. Silakan coba lagi.\n");
        }
    }

    public static int integerForm(String message) {
        while (true) {
            System.out.printf(message);
            String input = InputUtil.readLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= 0) return value;

                System.out.println("\n❌ Input tidak boleh negatif.\n");
            } catch (NumberFormatException e) {
                System.out.println("\n❌ Input harus berupa angka.\n");
            }
        }
    }

    public static String emailForm(String message) {
        while (true) {
            System.out.printf(message);
            String input = InputUtil.readLine().trim();

            if (input.isEmpty()) {
                System.out.println("\n❌ Input tidak boleh kosong.\n");
                continue;
            }

            if (input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return input;
            } else {
                System.out.println("\n❌ Format email tidak valid. Contoh: nama@email.com\n");
            }
        }
    }

    public static String phoneForm(String message) {
        while (true) {
            System.out.printf(message);
            String input = InputUtil.readLine().trim();

            if (input.isEmpty()) {
                System.out.println("\n❌ Input tidak boleh kosong.\n");
                continue;
            }

            if (input.matches("^[0-9]{10,15}$")) {
                
            } else {
                System.out.println("\n❌ Format nomor telepon tidak valid. Masukkan 10–15 digit angka.\n");
            }
        }
    }

    // Confirmation form
    public static boolean confirmationForm(String message) {
        while (true) {
            System.out.printf(message);
            String input = InputUtil.readLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes") || input.equals("ya")) {
                return true;
            } else if (input.equals("n") || input.equals("no") || input.equals("tidak")) {
                return false;
            } else {
                System.out.println("\n❌ Pilihan tidak valid. Silakan coba lagi.\n");
            }
        }
    }
}