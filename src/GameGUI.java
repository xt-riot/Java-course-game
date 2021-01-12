import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameGUI extends Panel {
    private boolean hasEnded;
    private Label questionLabel;
    private Buttons[] buttons;
    private Buttons spacer;
    private Label nextRoundLabel;

    private Timer timer;

    private boolean hasAnswers;
    private ArrayList<String> answers;
    private int clock;

    GameGUI(JFrame id, int numberOfPlayers) {
        super(id,true);
        this.questionLabel = new Label(id, "");
        this.buttons = new Buttons[numberOfPlayers];
        this.questionLabel = new Label(id, "");
        this.timer = new Timer(50, x->{});
        this.panels = 0;
        this.hasbuttons = true;
        this.hasAnswers = false;
        this.answers = new ArrayList<>();
        nextRoundLabel = new Label(this.frame, "New question in 3");

        this.questionLabel.setSize(Main.WIDTH - 100, 90);
        this.setLayout(null);
        this.questionLabel.setLocation(20, 20);
        //gbc.gridwidth = 15;
        //gbc.fill = GridBagConstraints.BOTH;
        this.add(this.questionLabel);

        this.buttons[0] = new Buttons(id, 4, true);
        this.buttons[0].setSize(150, 200);
        this.buttons[0].setLocation(20, 130);


        this.add(this.buttons[0]);
        this.buttons[1] = new Buttons(id, 4, true);
        this.buttons[1].setSize(150, 200);
        this.buttons[1].setLocation(200, 130);

        this.add(this.buttons[1]);

        //JPanel TestFiles.test = new JPanel();
        //TestFiles.test.setBackground(new Color(0,0,0,0));

        spacer = new Buttons(this.frame,1);
        //TestFiles.test.add(asd,gbc);
        this.add(spacer);

        this.buttons[0].addPropertyChangeListener("playerHasAnswered", e-> {
            System.out.println("player1 has chosen " + e.getNewValue());
            this.firePropertyChange("answer", 4, e.getNewValue());
        });
        this.buttons[1].addPropertyChangeListener("playerHasAnswered", x -> {
            System.out.println("player2 has chosen " + x.getNewValue());
            this.firePropertyChange("answer", 5, x.getNewValue());
        });
    }


    /**
     * Sets each button with the given answer.
     */
    public void setAnswers(ArrayList<String> possibleAnswers) {
        this.hasAnswers = true;
        this.answers = possibleAnswers;
        this.answers.remove(this.answers.size() - 1);
        System.out.println(this.answers);
        for (Buttons e : this.buttons)
            if( e != null )
                if (e.isShown())
                    e.setText(possibleAnswers);

    }


    public void prepareForNextQuestion(SingleQuestion question) {
        this.questionLabel.setText("Get ready! Next question...");
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            //this.questionLabel.setSize(new Dimension(Main.HEIGHT - 100, 90));
            this.questionLabel.setText(question.getQuestion());
            this.setAnswers(question.getAnswers());
            renderAll();
            System.out.println(this.timer.getInitialDelay());
            this.timer.setInitialDelay(0);
            this.timer.setDelay(1);
            this.timer.stop();
        });
        this.timer.setInitialDelay(0);
        this.timer.start();
    }

    public void prepareForNextRound(int round, String roundName, String roundInfo) {
        super.fadeIn(this.questionLabel);
        //System.out.println(this.questionLabel.getSize());
        //this.questionLabel.setLocation(new Point(0, 0));
        //this.questionLabel.setSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        this.questionLabel.setText(roundInfo);
        this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 2));
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            //System.out.println(temp + " // " + this.questionLabel.getLocation() + " // " + (this.questionLabel.getLocation() == temp));
            this.firePropertyChange("RoundScreenComplete", 0, 1);
            this.questionLabel.setSize(new Dimension(Main.WIDTH - 100, 90));
            this.questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            this.timer.setDelay(2000);
            //this.timer.stop();
        });
        this.timer.setInitialDelay(6000);
        this.timer.start();
    }
    public void setLabel(String txt) {
        this.questionLabel.setText(txt);
    }

    private void setListeners() {
        ActionListener[] asd = new ActionListener[4];
        asd[0] = e -> this.buttons[0].firePropertyChange("answer", 0, 1);
        asd[1] = e -> this.buttons[0].firePropertyChange("answer", 0, 2);
        asd[2] = e -> this.buttons[0].firePropertyChange("answer", 0, 3);
        asd[3] = e -> this.buttons[0].firePropertyChange("answer", 0, 4);

        this.buttons[0].setListeners(asd);
    }

    /**
     * Clones a player-designated area so the design doesn't break.
     */
    private void cloneArea() {
        System.out.println(this.spacer.getSize());
        System.out.println(this.buttons[0].getSize());
        this.spacer.setSize(this.buttons[0].getSize());
        System.out.println("CHANGED: " + this.spacer.getSize());

        //this.spacer.setLocation(this.buttons[0].getLocation());
        this.buttons[0].setLocation(new Point(400,400));
        this.buttons[1].setLocation(new Point(0,0));
        System.out.println(this.getComponentAt(this.buttons[0].getLocation()));
    }

    @Override
    public void startRendering() {
        super.startRenderingImage(150);
        this.rendering = true;
        renderAll();
    }

    private void renderAll() {

        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() - 1 && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
                System.out.println(this.getClass().getName() + " has completed fading in with all its components.");
                setListeners();
                this.timer.stop();
            } else if (this.panels < this.getComponentCount() && !this.rendering) {
                Component x = this.getComponent(this.panels);
                if (this.hasAnswers && x instanceof Buttons) ((Buttons) x).setText(answers);
                else if (x instanceof Buttons)
                    ((Buttons) x).setText(new ArrayList<>(Arrays.asList("answerA", "answerB", "answerC", "answerD")));
                super.fadeIn(x);
            }
        });
        this.timer.start();
    }

    public void startRenderingSingleComponent(Component x) {
        super.fadeIn(x);
    }


    public void unRenderSingleComponent(Component x) {

    }

    @Override
    public void unRender(int delay) {
        cloneArea();
    }
}
