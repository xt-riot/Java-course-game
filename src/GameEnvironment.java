// imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 */
class GameRounds {
    private ArrayList<String> gameRounds;
    private int score;
    private int roundIndex;

    /**
     * Ο κατασκευαστής της κλάσης.
     */
    public GameRounds() {
        gameRounds = new ArrayList<>();
        gameRounds.add("Correct Answer");
        gameRounds.add("Bet");
        score = 0;
        roundIndex = -1;
    }

    /**
     *Η μέθοδος BettingRound αποθηκεύει το ποσό που στοιχηματίζεται.
     * @return Επιστρέφει το ποσό που στοιχημάτησε ο χρήστης (score).
     */
    private int BettingRound() {
        Scanner temp = new Scanner(System.in);
        System.out.println();
        System.out.println("\t\t\tBefore answering, you have to choose how much you want to bet: ");
        System.out.println("\t\t\tBetting amounts: A) 250, B) 500, C) 750, D) 1000");
        String line = temp.nextLine();
        switch(line) {
            case("A"): score = 250; System.out.println("\tYou chose 250"); break;
            case("a"): score = 250; System.out.println("\tYou chose 250"); break;
            case("B"): score = 500; System.out.println("\tYou chose 500"); break;
            case("b"): score = 500; System.out.println("\tYou chose 500"); break;
            case("C"): score = 750; System.out.println("\tYou chose 750"); break;
            case("c"): score = 750; System.out.println("\tYou chose 750"); break;
            case("D"): score = 1000; System.out.println("\tYou chose 1000"); break;
            case("d"): score = 1000; System.out.println("\tYou chose 1000"); break;
            default: System.out.println("ERROR!!"); System.exit(-1);
        }
        return score;
    }

    /**
     *
     */
    public void RandomRound() {
        Random temp = new Random();
        roundIndex = temp.nextInt(gameRounds.size());
        switch(roundIndex) {
            case(0):
                System.out.println("Round: Correct answer.");
                System.out.println("If you guess the correct answer, you will get 1000 points.");
                System.out.println("");
                break;
            case(1):
                System.out.println("Round: Betting.");
                System.out.println("If you guess the correct answer, you will get the betted points,");
                System.out.println("otherwise you will lose them.");
                System.out.println("");
                break;
            case(2):
                System.out.println("ERROR!");
                break;
            case(-1):
                System.out.println("ERROR!");
                break;
        }
    }

    /**
     * Η Μέθοδος
     * @return Επιστρέφει το σκορ του παίκτη.
     */
    public int getScore() {
        switch(roundIndex) {
            case(0):    score = 1000; break;
            case(1):    score = BettingRound(); break;
        }
        return score;
    }
}

/**
 *
 */
public class GameEnvironment {
    private int roundCount;
    private int questionCount;
    private int playerCount;
    private ArrayList<String> categories;
    private ArrayList<Player> allPlayers;
    private AllQuestions allQuestions;
    private GameRounds round;

    /**
     *
     * @param rounds
     * @param questions
     * @param players
     */
    public GameEnvironment(int rounds, int questions, int players) {
        allQuestions = new AllQuestions();
        allQuestions.fillQuestions();

        roundCount = rounds;
        questionCount = questions;
        playerCount = players;

        categories = new ArrayList<>();
        String[] temp = allQuestions.getAllCategories();
        for (int i = 0; i < temp.length; i++) {
            if (allQuestions.getCategoryQuestions(temp[i]).size() >= questionCount) {
                categories.add(temp[i]);
            }
        }

        allPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter name of player" + (i + 1) + " : ");
            String userName = userInput.nextLine();
            allPlayers.add(new Player(userName, roundCount));
        }
    }
    public int getRoundCount() {
        return roundCount;
    }
    public int getQuestionCount() {
        return questionCount;
    }

    private SingleQuestion nextQuestion() {
        Random temp = new Random();
        SingleQuestion question;
        question = allQuestions.getRandomQuestion(categories.get(temp.nextInt(categories.size())));
        return question;
    }

    private void showQuestion(SingleQuestion question) {
        System.out.println(question.getQuestion());
    }

    /**
     * Η μέθοδος showAnswers εμφανίζει τις δυνατές απαντήσεις που έχει να επιλέξει μία από αυτές ο χρήστης.
     * @param question
     */
    private void showAnswers(SingleQuestion question) {
        System.out.println("Choose an answer:");
        System.out.println("A) " + question.getAnswers().get(0));
        System.out.println("B) " + question.getAnswers().get(1));
        System.out.println("C) " + question.getAnswers().get(2));
        System.out.println("D) " + question.getAnswers().get(3));
    }

    /**
     *
     */
    public void nextRound() {
        round = new GameRounds();
        round.RandomRound();
        for(int i = 0; i<questionCount; i++) {
            SingleQuestion question = nextQuestion();
            showQuestion(question);
            int score = round.getScore();
            showAnswers(question);
            allPlayers.get(0).setNewScore(question, score);
        }
    }

    /**
     * Η μέθοδος printTotalScore τυπώνει το συνολικό σκορ του παίκτη.
     */
    public void printTotalScore() {
        System.out.println();
        System.out.println("Total score of:");
        for(int i = 0; i < allPlayers.size(); i++) {
            System.out.println("- " + allPlayers.get(i).getName() + " is " + allPlayers.get(i).getScore() + " points.");
        }
    }


}
