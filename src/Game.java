import java.util.ArrayList;
import java.util.Random;

class GameRounds {
    private  ArrayList<String> gameRounds;
    private int score;
    private int roundIndex;

    public GameRounds() {
        gameRounds = new ArrayList<>();
        gameRounds.add("Correct Answer");
        gameRounds.add("Stop the counter");
        gameRounds.add("Bet");
        gameRounds.add("Fast answer");
        gameRounds.add("Thermometer");
        score = 0;
        roundIndex = -1;
    }

    public String getRoundName() {
        return this.gameRounds.get(roundIndex);
    }

    public String getRoundInfo() {
        String asd = "";
        switch (this.roundIndex) {
            case (0) -> asd += ("Correct answer: if you guess the correct answer, you will get 1000 points.");
            case (1) -> {
                asd += ("Stop the counter: here is a counter. Every player that answers correctly ");
                asd += ("earns points proportional to the time left in the counter.");
            }
            case (2) -> {
                asd += ("Bet: if you guess the correct answer, you will get the bet points, ");
                asd += ("otherwise you will lose them.");
            }
            case (3) -> {
                asd += ("Fast answer: he first player to answer correctly earns 1000 points ");
                asd += ("while the second 500.");
            }
            case (4) -> {
                asd += ("Thermometer: the first player to answer 5 questions correctly ");
                asd += ("earns 5000 points.");
            }
        }
        System.out.println(asd);
        return asd;
    }

    private int BettingRound() {
        String line = "a";
        line = line.toUpperCase();
        switch (line) {
            case ("A") -> {
                score = 250;
                System.out.println("\tYou chose 250");
            }
            case ("B") -> {
                score = 500;
                System.out.println("\tYou chose 500");
            }
            case ("C") -> {
                score = 750;
                System.out.println("\tYou chose 750");
            }
            case ("D") -> {
                score = 1000;
                System.out.println("\tYou chose 1000");
            }
            default -> {
                System.out.println("ERROR!!");
                System.exit(-1);
            }
        }
        return score;
    }


    public void RandomRound() {
        Random temp = new Random();
        this.roundIndex = temp.nextInt(gameRounds.size());
    }

    public int getScore() {
        int score = switch (this.roundIndex) {
            case (0) -> 1000;
            case (1) -> 1000;
            case (2) -> BettingRound();
            case (3) -> 1000;
            case (4) -> 1000;
            default -> 0;
        };
        return score;
    }
    public boolean isBettingRound() {
        return this.roundIndex == 2;
    }
}


public class Game {
    private int howManyRounds;
    private final int howManyPlayers;
    private int howManyQuestions;
    private GUILogic sd;
    private SingleQuestion questionPerPlayer;
    private final ArrayList<Player> players;
    private final AllQuestions allQuestions;
    private final GameRounds round;
    private final Random random;

    public Game(GUILogic howToDraw, ArrayList<Player> chosenPlayers) {
        this.sd = howToDraw;
        this.players = chosenPlayers;
        this.howManyPlayers = this.players.size();

        this.round = new GameRounds();
        this.allQuestions = new AllQuestions();
        this.allQuestions.fillQuestions();
        this.random = new Random();
        this.howManyRounds = this.random.nextInt(5) + 1;
        this.howManyQuestions = this.random.nextInt(5) + 1;

    }

    private void NextQuestion() {
        this.howManyQuestions--;
        ArrayList<SingleQuestion> questionsToChooseFrom = this.getRandomCategory();
        this.questionPerPlayer = allQuestions.getRandomQuestion(questionsToChooseFrom);
        questionPerPlayer.printObject();
        sd.prepare(questionPerPlayer);

    }

    public void startGame() {
        this.NextRound();
    }

    private void NextRound() {
        this.howManyRounds--;
        this.round.RandomRound();
        this.sd.nextRound(this.howManyRounds, this.round.getRoundName(), this.round.getRoundInfo(), this.howManyQuestions);
    }


    public void GetNextQuestion() {
        this.NextQuestion();
    }
    public void ReadyForNextStep(String[] answerPerPlayer) {
        this.addScores(answerPerPlayer);
        if (this.howManyQuestions > 0) {
            this.NextQuestion();
        } else if (this.howManyRounds > 0) {
            this.NextRound();
        } else this.EndOfGame();
    }

    public void EndOfGame() {
        String[] names = new String[this.players.size()];
        int[] scores = new int[this.players.size()];
        for(Player player : players) {
            System.out.println("* " + player.getName() + " is " + player.getScore() + " points.");
            System.out.println();
            player.getQuestionsAndResults();
            names[players.indexOf(player)] = player.getName();
            scores[players.indexOf(player)] = player.getScore();
        }
        sd.endOfGame(names, scores);
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

    public String printTotalScore(int number) {
        System.out.println();
        System.out.println("Total score of:");
        for (Player allPlayer : this.players) {
            System.out.println("* " + allPlayer.getName() + " is " + allPlayer.getScore() + " points.");
            System.out.println();
            allPlayer.getQuestionsAndResults();
        }
        return "Total score of player"+number+": " + this.players.get(number).getScore();
    }
}
