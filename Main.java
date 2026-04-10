import javax.swing.SwingUtilities;
import ui.HomeFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeFrame();
            }
        });
    }
}