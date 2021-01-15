import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Κλάση GUILogic η οποία δημιουργεί όλο το animation της εφαρμογής.
 */
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

    /**
     * Κατασκευαστής της κλάσης GUILogic.
     */
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
                try {
                    this.playerHasAnswered();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
     * Μέθοδος που προσθέτει ένα αρχικό πάνελ.
     * @param panelToAdd το πάνελ που προστίθεται.
     */
    public void addPrimaryPanel(Panel panelToAdd) {
        this.panelsToShow.add(panelToAdd);
    }

    /**
     * Μέθοδος επόμενου αρχικού πάνελ που θα εμφανιστεί.
     * @return επόμενο αρχικό πάνελ.
     */
    private Panel showNextPanel() {
        return this.panelsToShow.get(this.nextPanel);
    }


    /**
     * Μέθοδος που στέλνει την απάντηση στο GameEngine
     * και ενημερώνει τον παίκτη οτι έχει απαντήσει.
     */
    private void playerHasAnswered() throws IOException {
        this.game.ReadyForNextStep(answers);
    }

    /**
     * Μέθοδος που περιμένει απο τον χρήστη να δώσει
     * ένα όνομα στον παίκτη του παιχνιδιού.
     */
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

    /**
     * Μέθοδος που ετοιμάζει τις ερωτήσεις για τον παίκτη.
     * @param question
     */
    public void prepare(SingleQuestion question) {
        this.basicGame.prepareForNextQuestion(question);
        this.basicGame.invalidate();
        this.basicGame.validate();
        this.basicGame.repaint();
    }

    /**
     * Μέθοδος για τον επόνενο γύρο.
     * @param rounds γύρους παιχνιδιού
     * @param roundName όνομα του γύρου
     * @param roundInfo πληροφορίες του γύρου
     * @param questionsInRound ερώτηση που περιέχει ο γύρος
     */
    public void nextRound(int rounds, String roundName, String roundInfo, int questionsInRound) {
        this.basicGame.prepareForNextRound(rounds, roundName, roundInfo, questionsInRound);
    }

    public void setFrameVisible(boolean set) {
        frame.setVisible(set);
        this.animateIn();
    }

    /**
     * Μέθοδος που επιλέγει τον αριθμό παικτών.
     * @param p αριθμός παικτών
     */
    public void playersChosen(int p) {
        this.numberOfPlayers = p;
        this.basicGame.setPlayers(p);
        this.endScreen.setNumber(p);
        this.animateOut(0);
    }

    /**
     * Μέθοδος που αρχίσει να εμπλουτίζει (animate) το επόμενο
     * αρχικό πάνελ.
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

    /**
     * Μέθοδος που κρατάει πληροφορίες για τον/τους παίκτες.
     * @param names όνομα παικτη/ων
     * @param scores σκορ παίκτη/ων
     */
    public void endOfGame(String[] names, int[] scores) {
        this.endScreen.setLabels(names, scores, this.numberOfPlayers);
        this.nextPanel++;
        this.animateIn();
    }

}
