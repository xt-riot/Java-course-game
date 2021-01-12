import java.awt.event.KeyEvent;
import java.io.IOException;

public class Main {
    public static final int maximumQuestions = 3;
    public static final int maximumRounds = 3;
    public static final int maximumPlayers = 2;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;

    public static final int[] asd = {KeyEvent.VK_Q, KeyEvent.VK_W, KeyEvent.VK_E, KeyEvent.VK_R, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3, KeyEvent.VK_ENTER};

    public static void main(String[] args) throws IOException {

        //String filename = "storedData.csv";
        //new StoredData(filename);

        //h dis = new h();

        GUILogic gui = new GUILogic();
        gui.setFrameVisible(true);
        //gui.nextStep(0);


        /*Scanner userInput = new Scanner(System.in);
        String userName = "";
        while(!userName.equals("e")) {
            System.out.println("Time to shine ");
            userName = userInput.nextLine();
            if(userName.equals("y")) {
                gui.nextStep(0);
            }
            else if(userName.equals("n")) {
                gui.nextStep(1);
            }
            else if (userName.equals("1"))
                gui.nextStep(2);
            else if (userName.equals("3"))
                gui.nextStep(3);
        }//*/

        //GUI gui = new GUI();
        //gui.setFrameVisible(true);
        //gui.choosePlayers();
    }

}
