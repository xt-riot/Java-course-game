import javax.swing.*;

public class EndScreen extends Panel {
    private final Label topScreen;
    private Label[] players;

    EndScreen(JFrame id) {
        super(id, true);
        topScreen = new Label(id, "The game has ended. Let's see how you scored...");

        this.topScreen.setSize(Main.WIDTH - 100, 90);
        this.topScreen.setLocation(20, 20);
        this.add(topScreen);

        this.panels = 0;
    }

    public void setLabels(String[] playerNames, int[] playerScores, int numberOfPlayers) {
        this.setLayout(null);
        for(int i = 0; i < this.players.length; i++) {
            this.players[i] = new Label(this.frame, ("" + playerNames[i] + " scored " + playerScores[i] + " points!"));
            this.players[i].setSize(Main.WIDTH/this.players.length - 100, 70);
            this.players[i].setLocation(20 + 280*i, 70);
            this.add(this.players[i]);
        }
    }

    public void setNumber(int numberOfPlayers) {
        this.players = new Label[numberOfPlayers];
    }

    @Override
    public void startRendering() {
        super.startRenderingImage(150, true);
    }

    @Override
    public void unRender(int delay) {

    }
}
