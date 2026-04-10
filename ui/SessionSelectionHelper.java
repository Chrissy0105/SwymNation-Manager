package ui;

public class SessionSelectionHelper {

    private SessionSelectionHelper() {
    }

    public static int extractSessionId(String selectedItem) {
        if (selectedItem == null) {
            return -1;
        }

        try {
            String prefix = "Session ";
            int start = selectedItem.indexOf(prefix);
            int end = selectedItem.indexOf(" - ");

            if (start == -1 || end == -1) {
                return -1;
            }

            String idText = selectedItem.substring(start + prefix.length(), end).trim();
            return Integer.parseInt(idText);

        } catch (Exception e) {
            return -1;
        }
    }
}