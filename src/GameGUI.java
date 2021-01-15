import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Κλάση GameGUI που επεκτείνει την κλάση Panel.
 */
public class GameGUI extends Panel {
    private boolean bet;
    private boolean stopClock;
    private Label questionLabel;
    private final Buttons[] buttons;
    private int numberOfPlayers;

    private final Timer timer;

    private final ArrayList<String> answers;
    private int clockTick;
    private final Timer clock;
    private Label clockLabel;

    /**
     * Κατασκευαστής της κλάσης GameGUI.
     * @param id
     * @param numberOfPlayers αριθμός παικτών
     */
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

        this.keyListeners();
    }


    private void keyListeners() {
        this.buttons[0].addPropertyChangeListener("playerHasAnswered", e-> {
            if(this.stopClock) System.out.println("clicked at " + clockTick);
            this.firePropertyChange("answer", 4, e.getNewValue());
        });
        this.buttons[1].addPropertyChangeListener("playerHasAnswered", x -> {
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
                        firePropertyChange("answer", ((i/4) + 4), buttons[i/4].getAnswer(i%4) );
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

    /**
     * Μέθοδος για την προετοιμασία της επόμενης ερώτησης.
     * @param question επόμενη ερώτηση
     */
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
                this.questionLabel.setText("Get ready! Next question...");
                this.timer.removeActionListener(this.timer.getActionListeners()[0]);
                this.timer.addActionListener(x -> {
                    if(this.questionLabel.isRendering() && this.questionLabel.isShown()) {
                        this.timer.setInitialDelay(0);
                        this.timer.setDelay(1);
                        this.timer.stop();
                        if(this.stopClock) {
                            this.add(this.clockLabel);
                            this.clock.start();
                        }
                        this.setAnswers(question.getAnswers());
                        renderAll();
                        this.questionLabel.setText(question.getQuestion());
                    }
                });
                this.timer.start();
            }
        });
        this.timer.setInitialDelay(0);
        this.timer.start();
    }

    /**
     * Μέθοδος για την προετοιμασία του επόμενου γύρου.
     * @param round γύρος
     * @param roundName όνομα γύρου
     * @param roundInfo πληροφορίες γύρου
     * @param questions ερωτήσεις γύρου
     */
    public void prepareForNextRound(int round, String roundName, String roundInfo, int questions) {
        this.timer.stop();
        super.fadeIn(this.questionLabel);
        if(roundName.contains("Stop the clock")) { this.stopClock = true; this.bet = false; }
        else if(roundName.contains("Bet")) { this.bet = true; this.stopClock = false; }
        else { this.stopClock = false; this.bet = false; }
        this.questionLabel.setText(roundInfo);
        this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 2));
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            this.timer.stop();
            this.firePropertyChange("RoundScreenComplete", 0, 1);
            this.questionLabel.setSize(new Dimension(Main.WIDTH - 100, 90));
            this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        });
        this.timer.setInitialDelay(6000);
        this.timer.start();
    }

    /**
     * Μέθοδος για τον αριθμό των παικτών.
     * @param number αριθμός παικτών
     */
    public void setPlayers(int number) {
        this.numberOfPlayers = number;
    }

    /**
     * Μέθοδος για την κίνηση της εικόνας.
     */
    @Override
    public void startRendering() {
        super.startRenderingImage(150);
        this.rendering = true;
    }

    /**
     * Μέθοδος για την κίνηση της εικόνας.
     */
    private void renderAll() {
        for (int i = 0; i < this.numberOfPlayers; i++)
            this.add(this.buttons[i]);

        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() - 1 && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
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
