package TestFiles;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;

public class test {
    private JFrame frame;

    private GUILogic sd;
    private GameGUI op;

    private int players;
    private WelcomeScreen test;
    private PlayerChoice playerChoice;
    private Game game;

    public test() {
        frame = new JFrame("Buzz! Quiz World ripoff");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 400);

        //sd = new GUILogic(this.frame, this);

        //this.playerChoice = new PlayerChoice(this.frame, this);
        //this.TestFiles.test = new WelcomeScreen(this.frame, this);
        this.op = new GameGUI(this.frame, Main.maximumPlayers);
        this.op.addPropertyChangeListener("start", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("Property changed");
                if((int)evt.getNewValue() == 1) startG();
            }
        });


        //sd.addPrimaryPanel(this.TestFiles.test);
        //sd.addPrimaryPanel(this.playerChoice);
        sd.addPrimaryPanel(this.op);

        //sd.animateIn();

        //this.frame.setContentPane(TestFiles.test);
        //this.TestFiles.test.startRendering();

        //this.frame.add(playerChoice);
        //this.playerChoice.startRendering();

        frame.setLocationRelativeTo(null);
    }


    public void startG() {
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(new Player("kostas"));
        temp.add(new Player("Giannis"));
        //this.game = new Game(this, temp);
        //this.game.ReadyForNextStep();
    }

    public void prepare(SingleQuestion[] question) {
        this.op.setLabel(question[0].getQuestion());
        this.op.setAnswers(question[0].getAnswers());
        this.op.invalidate();
        this.op.validate();
        this.op.repaint();
    }

    public void setFrameVisible(boolean set) {
        //this.TestFiles.test.setVisibility(true);
        frame.setVisible(set);
        sd.animateIn();
    }

    public void nextStep(int b) {
        switch(b) {
            case(0): {
                this.frame.remove(this.playerChoice);
                //System.out.println(this.frame.getComponents().length);
                //this.frame.add(this.TestFiles.test);
                this.frame.repaint();
                //System.out.println(this.frame.getComponents().length);

                //this.TestFiles.test.repaint();
                //this.TestFiles.test.startRendering();
                break;
            }
            case(1): //this.TestFiles.test.unRender(); break;
            case(2): {

                //System.out.println(Arrays.toString(this.frame.getContentPane().getComponents()));
                //this.frame.getContentPane().remove(this.TestFiles.test);
                //this.playerChoice = new PlayerChoice(this.frame, this);
                //this.frame.getContentPane().add(this.playerChoice);
                this.frame.setContentPane(this.playerChoice);
                this.frame.invalidate();
                this.frame.validate();
                this.frame.repaint();
                //this.frame.getContentPane().paintComponents(this.playerChoice.getGraphics());
                //System.out.println(Arrays.toString(this.frame.getContentPane().getComponents()));
                this.playerChoice.startRendering();
                break;
            }
            case(3): this.playerChoice.unRender(0); break;
            case(4): break;
        }
    }

    public void playersChosen(int p) {
        this.players = p;
        System.out.println(p);
        sd.animateOut(0);
    }
}
