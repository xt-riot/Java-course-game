import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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

    public void NextQuestion() {
        this.howManyQuestions--;
        ArrayList<SingleQuestion> questionsToChooseFrom = this.getRandomCategory();
        this.questionPerPlayer = allQuestions.getRandomQuestion(questionsToChooseFrom);
        sd.prepare(questionPerPlayer);
    }

    public void startGame() {
        this.NextRound();
    }

    private void NextRound() {
        this.howManyRounds--;
        this.round.RandomRound();
        this.howManyQuestions = this.random.nextInt(Main.maximumQuestions) + 1;
        this.sd.nextRound(this.howManyRounds, this.round.getRoundName(), this.round.getRoundInfo(), this.howManyQuestions);
    }


    public void ReadyForNextStep(String[] answerPerPlayer) throws IOException {
        this.addScores(answerPerPlayer);
        if (this.howManyQuestions > 0) {
            this.NextQuestion();
        } else if (this.howManyRounds > 0) {
            this.NextRound();
        } else this.EndOfGame();
    }

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

    private ArrayList<SingleQuestion> getRandomCategory() {
        int howManyCategories = allQuestions.getAllCategories().length;
        int temp = this.random.nextInt(howManyCategories - 1);

        while (!(allQuestions.getCategoryQuestions(allQuestions.getAllCategories()[temp]).size() > this.howManyPlayers)) {
            temp = this.random.nextInt(howManyCategories - 1);
        }

        return allQuestions.getCategoryQuestions(allQuestions.getAllCategories()[temp]);
    }

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
