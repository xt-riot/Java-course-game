import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameGUI extends Panel {
    private boolean hasEnded;
    private boolean bet;
    private boolean stopClock;
    private Label questionLabel;
    private Buttons[] buttons;
    private Label nextRoundLabel;
    private int numberOfPlayers;

    private Timer timer;

    private ArrayList<String> answers;
    private int clockTick;
    private Timer clock;
    private Label clockLabel;

    GameGUI(JFrame id, int numberOfPlayers) {
        super(id,true);
        this.numberOfPlayers = numberOfPlayers;
        this.questionLabel = new Label(id, "");
        this.buttons = new Buttons[numberOfPlayers];
        this.questionLabel = new Label(id, "");
        this.timer = new Timer(50, x->{});
        this.panels = 0;
        this.hasbuttons = true;
        this.hasAnswers = false;
        this.answers = new ArrayList<>();
        nextRoundLabel = new Label(this.frame, "New question in 3");
        this.clockTick = 5000;
        this.clock = new Timer(1, e -> {
            clockTick--;
            this.clockLabel.setText("Clock: " + clockTick);
        });
        this.clockLabel = new Label(id, "Clock: ");
        this.stopClock = false;
        this.bet = false;

        this.questionLabel.setSize(Main.WIDTH - 100, 90);
        this.setLayout(null);
        this.questionLabel.setLocation(20, 20);
        this.add(this.questionLabel);

        this.buttons[0] = new Buttons(id, 4, true);
        this.buttons[0].setSize(150, 200);
        this.buttons[0].setLocation(20, 130);

        this.buttons[1] = new Buttons(id, 4, true);
        this.buttons[1].setSize(150, 200);
        this.buttons[1].setLocation(200, 130);


        this.buttons[0].addPropertyChangeListener("playerHasAnswered", e-> {
            System.out.println("player1 has chosen " + e.getNewValue());
            if(this.stopClock) System.out.println("clicked at " + clockTick);
            this.firePropertyChange("answer", 4, e.getNewValue());
        });
        this.buttons[1].addPropertyChangeListener("playerHasAnswered", x -> {
            System.out.println("player2 has chosen " + x.getNewValue());
            if(this.stopClock) System.out.println("clicked at " + clockTick);
            this.firePropertyChange("answer", 5, x.getNewValue());
        });

        this.frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                for ( int i = 0; i<Main.asd.length; i ++)
                    if(e.getKeyCode() == Main.asd[i]) {
                        System.out.println("!!!!! PRESSED: " + KeyEvent.getKeyText(Main.asd[i]) + " BY PLAYER " + (i / 4));
                        break;
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    /**
     * Sets each button with the given answer.
     */
    public void setAnswers(ArrayList<String> possibleAnswers) {
        this.hasAnswers = true;
        this.answers.addAll(possibleAnswers);
        this.answers.remove(this.answers.size() - 1);
        for (Buttons e : this.buttons)
            if( e != null )
                    e.setText(possibleAnswers);
    }

    public void prepareForNextQuestion(SingleQuestion question) {
        this.timer.stop();
        super.fadeOut(this.questionLabel);
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if(this.questionLabel.isRendering() && !this.questionLabel.isShown()) {
                this.timer.setInitialDelay(0);
                this.timer.setDelay(1);
                this.timer.stop();
                this.questionLabel.startRendering();
                System.out.println("New question shown with delay: " + this.timer.getInitialDelay() + this.timer.isRunning());
                this.questionLabel.setText("Get ready! Next question...");
                this.timer.removeActionListener(this.timer.getActionListeners()[0]);
                System.out.println(this.timer.isRunning());
                this.timer.addActionListener(x -> {
                    if(this.questionLabel.isRendering() && this.questionLabel.isShown()) {
                        this.timer.setInitialDelay(0);
                        this.timer.setDelay(1);
                        this.timer.stop();
                        //this.questionLabel.startRendering();//this.questionLabel.setSize(new Dimension(Main.HEIGHT - 100, 90));
                        System.out.println(this.questionLabel.isRendering());
                        if(this.stopClock) {
                            this.add(this.clockLabel);
                            this.clock.start();
                        }
                        this.setAnswers(question.getAnswers());
                        System.out.println(question.getAnswers());
                        renderAll();
                        this.questionLabel.setText(question.getQuestion());
                        System.out.println(this.timer.getInitialDelay());
                    }
                });
                this.timer.start();
            }
        });
        this.timer.setInitialDelay(0);
        this.timer.start();
    }

    public void prepareForNextRound(int round, String roundName, String roundInfo, int questions) {
        this.timer.stop();
        System.out.println("New round with " + questions + " questions " + this.timer.isRunning());
        super.fadeIn(this.questionLabel);
        if(roundName.contains("Stop the clock")) { this.stopClock = true; this.bet = false; }
        else if(roundName.contains("Bet")) { this.bet = true; this.stopClock = false; }
        else { this.stopClock = false; this.bet = false; }
        this.questionLabel.setText(roundInfo);
        this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 2));
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            //System.out.println(temp + " // " + this.questionLabel.getLocation() + " // " + (this.questionLabel.getLocation() == temp));
            this.timer.stop();
            //super.fadeOut(this.questionLabel);
            this.firePropertyChange("RoundScreenComplete", 0, 1);
            this.questionLabel.setSize(new Dimension(Main.WIDTH - 100, 90));
            this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            //this.timer.setInitialDelay(2000);
        });
        this.timer.setInitialDelay(6000);
        this.timer.start();
    }

    public void setPlayers(int number) {
        this.numberOfPlayers = number;
    }

    @Override
    public void startRendering() {
        super.startRenderingImage(150);
        this.rendering = true;
    }

    private void renderAll() {
        for (int i = 0; i < this.numberOfPlayers; i++)
            this.add(this.buttons[i]);

        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() - 1 && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
                System.out.println(this.getClass().getName() + " has completed fading in with all its components.");
                this.timer.stop();
            } else if (this.panels < this.getComponentCount() && !this.rendering) {
                super.fadeIn();
            }
        });
        this.timer.start();
    }

    @Override
    public void unRender(int delay) {
    }
}
