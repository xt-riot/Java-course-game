import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main {
    public static final int maximumQuestions = 3;
    public static final int maximumRounds = 3;
    public static final int maximumPlayers = 2;
    // There is a known problem with swing timers and CPU processing power:
    // lower values should make the background image fade in slower with worse CPUs
    // but will render the image fast with better CPUs
    public static final int speed = 10;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int[] asd = {KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_ENTER};

    public static void main(String[] args) throws IOException {

        GUILogic gui = new GUILogic();
        gui.setFrameVisible(true);

    }

}
