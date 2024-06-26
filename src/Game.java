import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Κλάση Game η οποία δημιουργεί την λογική του παιχνιδιού.
 */
public class Game {
    private int howManyRounds;
    private final int howManyPlayers;
    private int howManyQuestions;
    private final GUILogic sd;
    private SingleQuestion questionPerPlayer;
    private final ArrayList<Player> players;
    private final AllQuestions allQuestions;
    private final GameRounds round;
    private final Random random;
    private StoredData saver;

    /**
     * Κατασκευαστής της κλάσης Game.
     * @param howToDraw
     * @param chosenPlayers
     */
    public Game(GUILogic howToDraw, ArrayList<Player> chosenPlayers) {
        this.sd = howToDraw;
        this.players = chosenPlayers;
        this.howManyPlayers = this.players.size();

        this.round = new GameRounds();
        this.allQuestions = new AllQuestions();
        this.allQuestions.fillQuestions();
        this.random = new Random();
        this.howManyRounds = this.random.nextInt(Main.maximumRounds) + 1;
        this.saver = new StoredData();
    }

    /**
     * Μέθοδος για την επόμενη ερώτηση.
     */
    public void NextQuestion() {
        this.howManyQuestions--;
        ArrayList<SingleQuestion> questionsToChooseFrom = this.getRandomCategory();
        this.questionPerPlayer = allQuestions.getRandomQuestion(questionsToChooseFrom);
        sd.prepare(questionPerPlayer);
    }

    /**
     * Μέθοδος για την έναρξη του γύρου.
     */
    public void startGame() {
        this.NextRound();
    }

    /**
     * Μέθοδος για τον επόμενο γύρο.
     */
    private void NextRound() {
        this.howManyRounds--;
        this.round.RandomRound();
        this.howManyQuestions = this.random.nextInt(Main.maximumQuestions) + 1;
        this.sd.nextRound(this.howManyRounds, this.round.getRoundName(), this.round.getRoundInfo(), this.howManyQuestions);
    }

    /**
     * Μέθοδος
     * @param answerPerPlayer
     * @throws IOException
     */
    public void ReadyForNextStep(String[] answerPerPlayer) throws IOException {
        this.addScores(answerPerPlayer);
        if (this.howManyQuestions > 0) {
            this.NextQuestion();
        } else if (this.howManyRounds > 0) {
            this.NextRound();
        } else this.EndOfGame();
    }

    /**
     * Μέθοδος που μας δίνει τα ονόματα και σκορ των παικτών.
     * @throws IOException
     */
    public void EndOfGame() throws IOException {
        String[] names = new String[this.players.size()];
        int[] scores = new int[this.players.size()];
        for(Player player : players) {
            player.getQuestionsAndResults();
            names[players.indexOf(player)] = player.getName();
            scores[players.indexOf(player)] = player.getScore();
        }
        sd.endOfGame(names, scores);
        saver.saveData(names,scores);
    }

    /**
     * Μέθοδος για να παίρνουμε τυχαίες κατηφορίες.
     * @return
     */
    private ArrayList<SingleQuestion> getRandomCategory() {
        int howManyCategories = allQuestions.getAllCategories().length;
        int temp = this.random.nextInt(howManyCategories - 1);

        while (!(allQuestions.getCategoryQuestions(allQuestions.getAllCategories()[temp]).size() > this.howManyPlayers)) {
            temp = this.random.nextInt(howManyCategories - 1);
        }

        return allQuestions.getCategoryQuestions(allQuestions.getAllCategories()[temp]);
    }

    /**
     * Μέθοδος που ανανεώνει το σκορ τον παικτών με βάση της απαντήσεις.
     * @param answerPerPlayer
     */
    private void addScores(String[] answerPerPlayer) {
        int score = this.round.getScore();
        for (Player player : this.players) {
            int index = this.players.indexOf(player);
            if (this.questionPerPlayer.correctAnswer().equals(answerPerPlayer[index]))
                player.addNewScore(score);
            else if (this.round.isBettingRound())
                player.addNewScore((-1) * score);
            player.addAnsweredQuestion(this.questionPerPlayer, answerPerPlayer[index]);
        }

    }
}
