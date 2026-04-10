package ui;

public class ValidationHelper {

    private ValidationHelper() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isInteger(String value) {
        if (isEmpty(value)) {
            return false;
        }

        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        if (isEmpty(value)) {
            return false;
        }

        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLettersAndSpaces(String value) {
        return value != null && value.matches("[A-Za-z ]+");
    }

    public static boolean isEmail(String value) {
        return value != null && value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    public static boolean isCardNumber(String value) {
        return value != null && value.matches("\\d{13,19}");
    }

    public static boolean isCVV(String value) {
        return value != null && value.matches("\\d{3,4}");
    }

    public static boolean isExpiryDate(String value) {
        return value != null && value.matches("(0[1-9]|1[0-2])/\\d{2}");
    }
}