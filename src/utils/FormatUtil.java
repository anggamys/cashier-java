package utils;

import java.text.*;
import java.util.*;

import org.mindrot.jbcrypt.BCrypt;

public class FormatUtil {

    public static String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    public static String hashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    public static String formatCurrency(int amount) {
        return String.format("Rp %,d", amount);
    }

    public static int parseStrToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logError("FormatUtil", "parseStrToInt", e);
            return -1;
        }
    }

    public static Date parseStrToDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            logError("FormatUtil", "parseStrToDate", e);
            return null;
        }
    }

    public static String parseDateToStr(Date date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } catch (Exception e) {
            logError("FormatUtil", "parseDateToStr", e);
            return null;
        }
    }

    public static void logError(String className, String methodName, Exception e) {
        System.out.println("[" + className + ":" + methodName + "] ❌ " + e.getClass().getSimpleName() + " - " + e.getMessage());
        e.printStackTrace(); // Hapus ini jika tidak ingin terlalu verbose
    }
}
