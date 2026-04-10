package ui;

import javax.swing.JComboBox;
import java.util.List;

public class SelectionHelper {

    private SelectionHelper() {
    }

    public static void loadItems(JComboBox<String> comboBox, List<String> items) {
        comboBox.removeAllItems();

        for (int i = 0; i < items.size(); i++) {
            comboBox.addItem(items.get(i));
        }
    }

    public static String extractEmail(String selectedItem) {
        if (selectedItem == null) {
            return "";
        }

        int index = selectedItem.lastIndexOf(" - ");
        if (index == -1) {
            return "";
        }

        return selectedItem.substring(index + 3).trim();
    }
}