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
        //Scanner temp = new Scanner(System.in);
        //System.out.println();
        //System.out.println("\t\tBefore answering, you have to choose how much you want to bet: ");
        //System.out.println("\t\tBetting amounts: A) 250, B) 500, C) 750, D) 1000");
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
        int score = 500;
        switch(this.roundIndex) {
            case(0):    score = 1000; break;
            case(1):    break;
            case(2):    score = BettingRound(); break;
            case(3):    break;
            case(4):    break;
        }
        return score;
    }
    public boolean isBettingRound() {
        return this.roundIndex == 2;
    }
}


public class Game {
    //public static int maximumQuestions = 3;
    //public static int maximumRounds = 3;

    private int howManyRounds;
    private final int howManyPlayers;
    private int howManyQuestions;
    private GUILogic sd;
    private SingleQuestion questionPerPlayer;
    //private ArrayList<String> categories;
    private final ArrayList<Player> players;
    private final AllQuestions allQuestions;
    private final GameRounds round;
    private final Random random;

    /*ublic Game(GUI howToDraw, ArrayList<Player> chosenPlayers) {
        this.draw = howToDraw;
        this.players = chosenPlayers;
        this.howManyPlayers = this.players.size();

        this.round = new GameRounds();
        //this.questionPerPlayer = new SingleQuestion();
        this.allQuestions = new AllQuestions();
        this.allQuestions.fillQuestions();
        this.random = new Random();
        this.howManyRounds = 3;
        this.howManyQuestions = 0;

    }//*/

    public Game(GUILogic howToDraw, ArrayList<Player> chosenPlayers) {
        this.sd = howToDraw;
        this.players = chosenPlayers;
        this.howManyPlayers = this.players.size();

        this.round = new GameRounds();
        //this.questionPerPlayer = new SingleQuestion[chosenPlayers.size()];
        this.allQuestions = new AllQuestions();
        this.allQuestions.fillQuestions();
        this.random = new Random();
        this.howManyRounds = 3;
        this.howManyQuestions = 0;

    }

    public void StartGame() {
        /*
        - GUI has initialized Game() and this class takes control
        - Tell GUI to draw starting screen
        - when program returns here, start the game while rounds > 0 choosing a random round each iteration

        - invoke EndOfGame
         */

    }

    private void NextQuestion() {
        /*
        - Get a new category
        - Get a new pair of questions according to the given category
        - Tell GUI to draw the questions
         */
        this.howManyQuestions--;
        ArrayList<SingleQuestion> questionsToChooseFrom = this.getRandomCategory();
        this.questionPerPlayer = allQuestions.getRandomQuestion(questionsToChooseFrom);
        sd.prepare(questionPerPlayer);

    }

    public void startGame() {
        //System.out.println(this.howManyRounds + " // " + this.howManyQuestions);
        this.NextRound();
    }

    private void NextQuestion(int k) {
        /*
        - Get a new category
        - Get a new pair of questions according to the given category
        - Tell GUI to draw the questions
         */
        this.howManyQuestions--;
        ArrayList<SingleQuestion> questionsToChooseFrom = this.getRandomCategory();
        this.questionPerPlayer = allQuestions.getRandomQuestion(questionsToChooseFrom);
        sd.prepare(questionPerPlayer);

    }

    private void NextRound() {
        /*
        - set next round
        - invoke NextQuestion to get new questions and show them
        - add questions, answers and score to Player database
         */
        this.howManyRounds--;
        this.howManyQuestions = random.nextInt(Main.maximumQuestions);
        this.round.RandomRound();
        //System.out.println("ROUND: " + this.round.getRoundInfo());
        this.NextQuestion();
        //this.sd.nextRound(this.howManyRounds, this.round.getRoundName(), this.round.getRoundInfo(), this.howManyQuestions);
    }


    public void GetNextQuestion() {
        /*
        - provide Player.java the question, the answer and a new score for each player
         */
        this.NextQuestion();
    }
    public void ReadyForNextStep(String[] answerPerPlayer) {
        /*
        - provide Player.java the question, the answer and a new score for each player
         */
        this.addScores(answerPerPlayer);
        if (this.howManyQuestions > 0) {
            this.NextQuestion();
        } else if (this.howManyRounds > 0) {
            this.NextRound();
        } else this.EndOfGame();
    }

    private void EndOfGame() {
        /*
        - end of game
         */

        sd.endOfGame();
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
            //String choice = this.questionPerPlayer.getAnswers().get(answerPerPlayer[index]);
            if (this.questionPerPlayer.correctAnswer().equals(answerPerPlayer[index]))
                player.addNewScore(score);
            else if (this.round.isBettingRound())
                player.addNewScore((-1) * score);
            player.addAnsweredQuestion(this.questionPerPlayer, answerPerPlayer[index]);
        }

    }

    private String intToString(int incoming) {
        return switch (incoming) {
            case (0) -> "A";
            case (1) -> "B";
            case (2) -> "C";
            case (3) -> "D";
            default -> "Error";
        };
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
