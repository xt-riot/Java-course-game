import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GUILogic {
    private ArrayList<Panel> panelsToShow;
    private JFrame frame;
    private int nextPanel;
    private boolean isPanelShown;
    private int currentPanelHasComponents;
    private int howManyPlayersAnswered;
    private int numberOfPlayers;

    private ArrayList<Player> allPlayers;

    private Timer timer;

    private WelcomeScreen welcomeScreen;
    private PlayerChoice playerChoice;


    private GameGUI basicGame;
    private Game game;
    private String[] answers;

    GUILogic() {
        this.panelsToShow = new ArrayList<>();
        this.nextPanel = 0;
        this.currentPanelHasComponents = 0;
        this.isPanelShown = false;
        this.timer = new Timer(150, e->{});
        this.howManyPlayersAnswered = 0;
        this.allPlayers = new ArrayList<>();

        // CHANGE
        //this.numberOfPlayers = 2;
        //this.allPlayers.add(new Player("kostas"));
        //this.allPlayers.add(new Player("kostas"));
        // CHANGE

        this.frame = new JFrame("Buzz! Quiz World ripoff");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(800, 400);

        this.playerChoice = new PlayerChoice(this.frame, this);
        this.welcomeScreen = new WelcomeScreen(this.frame, this);
        this.basicGame = new GameGUI(this.frame, Main.maximumPlayers);

        this.basicGame.addPropertyChangeListener("answer", e -> {
            this.answers[((int)e.getOldValue() - 4)] = (String) e.getNewValue();
            this.howManyPlayersAnswered++;
            System.out.println(this.howManyPlayersAnswered+ " have answered already out of " + this.numberOfPlayers + " players in game.");
            if(this.howManyPlayersAnswered == (this.numberOfPlayers)) {
                //System.out.println("Got that a player has answered: player" + this.answers[0] + " has answered " + this.answers[1]);
                this.playerHasAnswered();
                this.howManyPlayersAnswered = 0;
                this.answers = new String[this.numberOfPlayers];
            }
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

        //this.addPrimaryPanel(welcomeScreen);
        this.addPrimaryPanel(playerChoice);
        this.addPrimaryPanel(basicGame);

        //this.answers = new int[this.numberOfPlayers];
        //this.game = new Game(this, this.allPlayers);
        //this.game.startGame();

        this.basicGame.addPropertyChangeListener("RoundScreenComplete", e->{
            this.getNextQuestion();
            //this.basicGame.prepareForNextQuestion();
            //this.game.ReadyForNextStep();
        });
        this.basicGame.addPropertyChangeListener("QuestionScreenComplete", e->{
            //this.game.ReadyForNextStep();
        });
        this.frame.setLocationRelativeTo(null);

    }

    /**
     * Adds a primary panel to the queue.
     * @param panelToAdd the Panel to add.
     */
    public void addPrimaryPanel(Panel panelToAdd) {
        this.panelsToShow.add(panelToAdd);
    }

    /**
     * Next primary panel to be shown.
     * @return The next primary panel.
     */
    private Panel showNextPanel() {
        return this.panelsToShow.get(this.nextPanel);
    }




    /**
     * Send the answer to the GameEngine to notify that a player has answered
     */
    private void playerHasAnswered() {
        this.game.ReadyForNextStep(answers);
    }

    public void getNextQuestion() {
        this.game.GetNextQuestion();
    }

    /**
     * Gui is ready to show the next question(all players have answered).
     * Changes the question label of GameGUI
     * Makes the appropriate changes to the GUI(changes answers, resets timer)
     */
    /*public void changeQuestion() {
        this.GAMEENGINE.getNextQuestion();
        ArrayList answers = question.getAnswers();
        this.GAMEGUI.setLabel(question.getQuestion());
        this.GAMEGUI.setAnswers(player, answers);
        this.GAMEGUI.showPanel(player);
        Collections.shuffle(answers);
        this.GAMEGUI.setAnswers(player, answers);
        this.GAMEGUI.showPanel(player);
        this.GAMEGUI.invalidate();
        this.GAMEGUI.validate();
        this.GAMEGUI.repaint();
    }//*/

    public void startG() {
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(new Player("kostas"));
        temp.add(new Player("Giannis"));
        this.game = new Game(this, temp);
        //this.game.ReadyForNextStep();
    }


    public void choosePlayerName() {
        //this.setFrameVisible(false);
        for(int i = 1; i<=this.numberOfPlayers; i++) {
            String name = JOptionPane.showInputDialog(frame, "Choose the name of player"+i, "Player"+i+" name", JOptionPane.QUESTION_MESSAGE);
            if (name.length() > 0)
                allPlayers.add(new Player(name));
            else
                allPlayers.add(new Player("Player"+i));
        }
        this.nextPanel++;
        animateIn();
        //setFrameVisible(true);
        System.out.println(allPlayers);
        this.answers = new String[this.numberOfPlayers];
        this.game = new Game(this, this.allPlayers);
        this.game.startGame();
    }
    public void prepare(SingleQuestion question) {
        this.basicGame.setLabel(question.getQuestion());
        this.basicGame.setAnswers(question.getAnswers());
        this.basicGame.invalidate();
        this.basicGame.validate();
        this.basicGame.repaint();
    }

    public void nextRound(int rounds, String roundName, String roundInfo, int questionsInRound) {
        this.basicGame.prepareForNextRound(rounds, roundName, roundInfo);
    }

    public void setFrameVisible(boolean set) {
        //this.TestFiles.test.setVisibility(true);
        frame.setVisible(set);
        this.animateIn();
    }

    public void playersChosen(int p) {
        this.numberOfPlayers = p;
        //System.out.println(p);
        this.animateOut(0);
    }

    /**
     * Starts animating in the next primary Panel.
     */
    public void animateIn() {
        Panel temporaryVarForPanel = this.showNextPanel();
        this.frame.setContentPane(temporaryVarForPanel);
        this.frame.invalidate();
        this.frame.validate();
        this.frame.repaint();
        //System.out.println(temporaryVarForPanel);
        System.out.println(this.frame.getContentPane());
        this.currentPanelHasComponents = 0;
        temporaryVarForPanel.startRendering();
        ActionListener action = e -> {
            //System.out.println(temporaryVarForPanel.getClass().getName() + "'s timer. Panel is: " + this.panelsToShow.size() + " // " + this.nextPanel);
            if (!temporaryVarForPanel.isShown()) {
                //System.out.println(temporaryVarForPanel.getComponent(currentPanelHasComponents).getClass().getName());
                //animateIn((Panel) temporaryVarForPanel.getComponent(currentPanelHasComponents));
                temporaryVarForPanel.startRendering();
            } else if (temporaryVarForPanel.isShown()) {
                this.timer.stop();
                if (!temporaryVarForPanel.hasButtons()) {
                    System.out.println(this.getClass().getName() + " has requested " + temporaryVarForPanel.getClass().getName() + " to fade out because it has no buttons");
                    this.animateOut(0);
                }
            }
        };
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(action);
        this.timer.start();


        //temporaryVarForPanel.startRendering();
        //this.isPanelShown = true;
    }

    /**
     * Starts animating in a secondary Panel.
     * @param panelToAnimateIn the panel to animate
     */
    public void animateIn(Panel panelToAnimateIn) {

        //this.frame.setContentPane(panelToAnimateIn);
        //this.frame.invalidate();
        //this.frame.validate();
        //this.frame.repaint();
        if(!panelToAnimateIn.isShown() && panelToAnimateIn.isRendering()) {
            //System.out.println(panelToAnimateIn.getClass().getName() + " is not shown.");
            panelToAnimateIn.startRendering();
        }
        else if(panelToAnimateIn.isShown()) {
            System.out.println(panelToAnimateIn.getClass().getName() + " is shown with " + panelToAnimateIn.getComponentCount() +" components.(GUILogic)");
            this.currentPanelHasComponents += panelToAnimateIn.getComponentCount();
        }

    }

    /**
     * Starts animating out the current primary Panel shown
     * The animation starts with the last child-component the panel has
     *      then animates out the primary Panel's image.
     */
    public void animateOut(int delay) {

        Panel temporaryVarForPanel = this.showNextPanel();
        temporaryVarForPanel.unRender(delay);

        ActionListener action = e -> {
            if (!temporaryVarForPanel.isShown() && temporaryVarForPanel.isRendering() && this.panelsToShow.size() != (this.nextPanel + 1) && !temporaryVarForPanel.hasButtons()) {
                System.out.println(this.getClass().getName() + " will show the next panel as soon as this unrenders.");
                this.nextPanel++;
                animateIn();
                this.timer.stop();
            } else if (!temporaryVarForPanel.isShown && temporaryVarForPanel.isRendering() && this.panelsToShow.size() == (this.nextPanel + 1)) {
                System.out.println(this.getClass().getName() + " has no more panels to show.");
                this.timer.stop();
            }
        };
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(action);
        this.timer.start();
    }




    /**
     * Starts animating out a secondary panel(questions, answers etc)
     * @param panelToAnimateOut the panel to animate out
     */
    public void animateOut(Panel panelToAnimateOut) {
        if(panelToAnimateOut.isShown() && panelToAnimateOut.isRendering()) {
            panelToAnimateOut.unRender(0);
            System.out.println(panelToAnimateOut.getClass().getName() + " has been ordered to fade out.(GUILogic)");
        }
        else if(!panelToAnimateOut.isShown()) {
            //System.out.println(panelToAnimateOut.getClass().getName() + " is not shown.");
            this.currentPanelHasComponents--;
        }
    }

    public void endOfGame() {
        //end of game
    }

}
