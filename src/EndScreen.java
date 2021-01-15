import javax.swing.*;
import java.awt.*;

public class EndScreen extends Panel {
    private Label topScreen;
    private Label[] players;

    EndScreen(JFrame id) {
        super(id, true);
        topScreen = new Label(id, "The game has ended. Let's see how you scored...");

        this.topScreen.setSize(Main.WIDTH - 100, 90);
        this.topScreen.setLocation(20, 20);


        /*this.players = new Label[2];
        this.players[0] = new Label(id, "gtxm");
        this.players[1] = new Label(id, "gtpm");
        this.players[0].setLocation(0, 130);
        this.players[0].setSize(Main.WIDTH - 100, 90);

        this.players[1].setLocation(200, 130);
        this.players[1].setSize(Main.WIDTH - 100, 90);//*/


        this.add(topScreen);
        //this.add(players[0]);
        //this.add(players[1]);

        this.panels = 0;
    }

    public void setLabels(String[] playerNames, int[] playerScores, int numberOfPlayers) {
        //this.players = new Label[numberOfPlayers];
        this.setLayout(null);
        for(int i = 0; i < this.players.length; i++) {
            this.players[i] = new Label(this.frame, ("" + playerNames[i] + " scored " + playerScores[i] + " points!"));
            this.players[i].setSize(Main.WIDTH/this.players.length - 100, 70);
            //this.players[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 3));
            this.players[i].setLocation(20 + 280*i, 70);
            this.add(this.players[i]);
            //System.out.println(this.players[i].getSize());
        }

       //super.fadeIn();
    }

    public void setNumber(int numberOfPlayers) {

        this.players = new Label[numberOfPlayers];


    }

    @Override
    public void startRendering() {
        //this.imgCurrentPosition = 150;
        super.startRenderingImage(150, true);
        // tha sou emfanistei miafora, ta animations tha ta allaksw meta
        // pekse mpala mono me to pws fainetai h klasi sou
    }

    @Override
    public void unRender(int delay) {

    }
}
