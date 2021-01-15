import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUILogic {
    private final ArrayList<Panel> panelsToShow;
    private final JFrame frame;
    private int nextPanel;
    private int howManyPlayersAnswered;
    private int numberOfPlayers;
    private final ArrayList<Player> allPlayers;
    private final Timer timer;
    private final WelcomeScreen welcomeScreen;
    private final PlayerChoice playerChoice;

    private final GameGUI basicGame;
    private final EndScreen endScreen;
    private Game game;
    private String[] answers;

    GUILogic() {
        this.panelsToShow = new ArrayList<>();
        this.nextPanel = 0;
        this.timer = new Timer(150, e->{});
        this.howManyPlayersAnswered = 0;
        this.allPlayers = new ArrayList<>();

        this.frame = new JFrame("Buzz! Quiz World ripoff");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.setSize(800, 400);

        this.playerChoice = new PlayerChoice(this.frame, this);
        this.welcomeScreen = new WelcomeScreen(this.frame);
        this.basicGame = new GameGUI(this.frame, Main.maximumPlayers);
        this.endScreen = new EndScreen(this.frame);

        this.basicGame.addPropertyChangeListener("answer", e -> {
            this.answers[((int)e.getOldValue() - 4)] = (String) e.getNewValue();
            this.howManyPlayersAnswered++;
            if(this.howManyPlayersAnswered == (this.numberOfPlayers)) {
                this.playerHasAnswered();
                this.howManyPlayersAnswered = 0;
                this.answers = new String[this.numberOfPlayers];
            }
        });


        this.addPrimaryPanel(welcomeScreen);
        this.addPrimaryPanel(playerChoice);
        this.addPrimaryPanel(basicGame);
        this.addPrimaryPanel(endScreen);

        this.basicGame.addPropertyChangeListener("RoundScreenComplete", e-> this.game.NextQuestion());
        this.basicGame.addPropertyChangeListener("QuestionScreenComplete", e->{ });
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

    public void choosePlayerName() {
        for(int i = 1; i<=this.numberOfPlayers; i++) {
            String name = JOptionPane.showInputDialog(frame, "Choose the name of player"+i, "Player"+i+" name", JOptionPane.QUESTION_MESSAGE);
            if (name.length() > 0)
                allPlayers.add(new Player(name));
            else
                allPlayers.add(new Player("Player"+i));
        }
        this.nextPanel++;
        animateIn();
        this.answers = new String[this.numberOfPlayers];
        this.game = new Game(this, this.allPlayers);
        this.game.startGame();
    }
    public void prepare(SingleQuestion question) {
        this.basicGame.prepareForNextQuestion(question);
        this.basicGame.invalidate();
        this.basicGame.validate();
        this.basicGame.repaint();
    }

    public void nextRound(int rounds, String roundName, String roundInfo, int questionsInRound) {
        this.basicGame.prepareForNextRound(rounds, roundName, roundInfo, questionsInRound);
    }

    public void setFrameVisible(boolean set) {
        frame.setVisible(set);
        this.animateIn();
    }

    public void playersChosen(int p) {
        this.numberOfPlayers = p;
        this.basicGame.setPlayers(p);
        this.endScreen.setNumber(p);
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
        temporaryVarForPanel.startRendering();
        ActionListener action = e -> {
            if (!temporaryVarForPanel.isShown()) {
                temporaryVarForPanel.startRendering();
            } else if (temporaryVarForPanel.isShown()) {
                this.timer.stop();
                if (temporaryVarForPanel.hasButtons() && this.nextPanel < (this.panelsToShow.size()-1)) {
                    this.animateOut(0);
                }
            }
        };
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(action);
        this.timer.start();
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
            if (!temporaryVarForPanel.isShown() && temporaryVarForPanel.isRendering() && this.panelsToShow.size() != (this.nextPanel + 1) && temporaryVarForPanel.hasButtons()) {
                this.nextPanel++;
                animateIn();
                this.timer.stop();
            } else if (!temporaryVarForPanel.isShown && temporaryVarForPanel.isRendering() && this.panelsToShow.size() == (this.nextPanel + 1)) {
                this.timer.stop();
            }
        };
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(action);
        this.timer.start();
    }

    public void endOfGame(String[] names, int[] scores) {
        this.endScreen.setLabels(names, scores, this.numberOfPlayers);
        this.nextPanel++;
        this.animateIn();
    }

}
