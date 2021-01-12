import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerArea extends Panel {
    private boolean hasEnded;
    private Label questionLabel;
    private Buttons[] buttons;
    private Buttons spacer;
    private Label nextQuestionLabel;
    private Label nextRoundLabel;

    private Timer timer;

    private boolean hasAnswers;
    private ArrayList<String> answers;
    private Timer clock;
    private int clockTime;

    PlayerArea(JFrame id, int numberOfPlayers) {
        super(id, true);
        this.questionLabel = new Label(id, "");
        this.buttons = new Buttons[numberOfPlayers];
        this.questionLabel = new Label(id, "Question goes here");
        this.timer = new Timer(50, x->{});
        this.panels = 0;
        this.hasbuttons = true;
        this.hasAnswers = false;
        this.answers = new ArrayList<>();
        nextQuestionLabel = new Label(this.frame, "Next question in ");
        nextRoundLabel = new Label(this.frame, "asd");
        this.clock = new Timer(1000, e -> {});

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
    //public void setAnswers(ArrayList<String> possibleAnswers) {
    //    this.answerButtons.setText(possibleAnswers);
    //}
    /**
     * Once a player has answered to a question this method clones the area, hides the buttons and sends the answered to GUILogic
     */
    private void playerAnswered() {

    }

    /**
     * Clones a player-designated area so the design doesn't break.
     */
    private void cloneArea() {

    }
    public void setAnswers(ArrayList<String> possibleAnswers) {

    }

    public void prepareForNextQuestion() {
        this.add(this.nextQuestionLabel);
        this.nextQuestionLabel.setSize(Main.WIDTH - 100, 90);
        this.nextQuestionLabel.setLocation(20, 120);


        clockTime = 3;
        this.clock.removeActionListener(this.clock.getActionListeners()[0]);
        this.clock.addActionListener(x -> {
            this.nextQuestionLabel.setText("<html>Next question in " + clockTime + "<br><p>Get Ready!</html>");
            this.invalidate();
            this.validate();
            this.repaint();
            clockTime--;
            if(clockTime == 0) {
                this.nextQuestionLabel.unRender(0);
            }
            else if (clockTime < -1 && !this.nextQuestionLabel.isShown()) {
                this.remove(this.nextQuestionLabel);
                this.rendering = false;
                this.firePropertyChange("QuestionScreenComplete", 0, 1);
                this.renderAll();
                this.clock.stop();
            }
        });

        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e->{
            if (!this.questionLabel.isShown() && !this.questionLabel.isRendering() && this.isShown && !this.rendering && !this.nextQuestionLabel.isShown() && !this.nextQuestionLabel.isRendering()) {
                super.fadeIn(this.nextQuestionLabel);
                this.rendering = true;
                nextQuestionLabel.setCounted(true);
            }
            else if(nextQuestionLabel.isShown()) {
                this.clock.start();
                this.timer.stop();
            }
        });
        this.timer.start();
        this.timer.setDelay(1);
    }

    
    public void prepareForNextRound(int rounds, String roundName, String roundInfo, int questions) {
        if(this.questionLabel.isShown() && !this.questionLabel.isRendering()) this.questionLabel.unRender(0);

        //super.fadeIn(this.nextRoundLabel);

        clockTime = 8;
        this.clock.removeActionListener(this.clock.getActionListeners()[0]);
        this.clock.addActionListener(x -> {
            this.nextRoundLabel.setText("<html><h1>Remaining rounds: " + rounds + "</h1><p><h3>" + roundName + "</h3><p>" + roundInfo + "<br><br><br>This round will have " + questions + " questions.</html>");
            //this.nextRoundLabel.setText(rounds + " // " + roundName + " // " + roundInfo + " // " + questions);
            this.invalidate();
            this.validate();
            this.repaint();
            clockTime--;
            if(clockTime == 0) {
                this.nextRoundLabel.unRender(0);
                this.firePropertyChange("RoundScreenComplete", 0, 1);
            }
            else if (clockTime < 0 && !this.nextRoundLabel.isShown()) {
                this.remove(this.nextRoundLabel);
                this.clock.stop();
            }
        });

        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e->{
            if (!this.questionLabel.isShown() && !this.questionLabel.isRendering() && this.isShown && !this.nextRoundLabel.isShown() && !this.nextRoundLabel.isRendering()) {
                this.add(this.nextRoundLabel);
                this.nextRoundLabel.setSize(Main.WIDTH, Main.HEIGHT);
                this.nextRoundLabel.setLocation(20, 20);
                super.fadeIn(this.nextRoundLabel);
                nextRoundLabel.setCounted(true);
            }
            else if(nextRoundLabel.isShown()) {
                System.out.println("HEy");
                this.clock.start();
                this.timer.stop();
            }
        });
        this.timer.start();
        this.timer.setDelay(1);
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
    public void startRendering() {
        this.imgCurrentPosition = 150;
        super.startRenderingImage(150, false);
        this.rendering = true;
    }

    @Override
    public void unRender(int delay) {
        cloneArea();
    }
}
