import java.util.*;


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

    private int BettingRound() {
        Scanner temp = new Scanner(System.in);
        System.out.println();
        System.out.println("\t\tBefore answering, you have to choose how much you want to bet: ");
        System.out.println("\t\tBetting amounts: A) 250, B) 500, C) 750, D) 1000");
        String line = temp.nextLine();
        line = line.toUpperCase();
        switch(line) {
            case("A"): score = 250; System.out.println("\tYou chose 250"); break;
            case("B"): score = 500; System.out.println("\tYou chose 500"); break;
            case("C"): score = 750; System.out.println("\tYou chose 750"); break;
            case("D"): score = 1000; System.out.println("\tYou chose 1000"); break;
            default: System.out.println("ERROR!!"); System.exit(-1);
        }
        return score;
    }


    public int RandomRound() {
        Random temp = new Random();
        roundIndex = temp.nextInt(gameRounds.size());
        switch(roundIndex) {
            case(0):
                System.out.println("Round: Correct answer.");
                System.out.println("If you guess the correct answer, you will get 1000 points.");
                System.out.println();
                break;
            case(1):
                System.out.println("Round: Stop the counter.");
                System.out.println("There is a counter. Every player that answers correctly");
                System.out.println("earns points proportional to the time left in the counter.");
                break;
            case(2):
                System.out.println("Round: Betting.");
                System.out.println("If you guess the correct answer, you will get the bet points,");
                System.out.println("otherwise you will lose them.");
                System.out.println();
                break;
            case(3):
                System.out.println("Round: Fast answer.");
                System.out.println("The first player to answer correctly earns 1000 points");
                System.out.println("while the second 500.");
                break;
            case(4):
                System.out.println("Round: Thermometer:");
                System.out.println("The first player to answer 5 questions correctly");
                System.out.println("earns 5000 points.");
                break;
        }
        return roundIndex;
    }

    public int getScore() {

        return score;
    }
}

public class GameEnvironment {
    private int roundCount;
    private int questionCount;
    private int playerCount;
    private int round;
    private ArrayList<String> categories;
    private ArrayList<Player> allPlayers;
    private AllQuestions allQuestions;

    public GameEnvironment(int rounds, int questions, int players) {
        allQuestions = new AllQuestions();
        allQuestions.fillQuestions();

        roundCount = rounds;
        questionCount = questions;
        playerCount = players;

        categories = new ArrayList<>();
        String[] temp = allQuestions.getAllCategories();
        for (String s : temp) {
            if (allQuestions.getCategoryQuestions(s).size() >= questionCount) {
                categories.add(s);
            }
        }

        allPlayers = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter name of player" + (i + 1) + " : ");
            String userName = userInput.nextLine();
            allPlayers.add(new Player(userName));
        }
    }
    public int getRoundCount() {
        return roundCount;
    }
    public int getQuestionCount() {
        return questionCount;
    }

    private String getRandomCategory() {
        Random temp = new Random();
        int random = temp.nextInt( categories.size() );
        return categories.get(random);
    }

    private void showQuestion(SingleQuestion question) {
        System.out.println(question.getQuestion());
        System.out.println("Choose an answer:");
        System.out.println("A) " + question.getAnswers().get(0));
        System.out.println("B) " + question.getAnswers().get(1));
        System.out.println("C) " + question.getAnswers().get(2));
        System.out.println("D) " + question.getAnswers().get(3));

    }

    public void nextRound() {
        this.round = new GameRounds().RandomRound();
        if(round != 5) {
            for (int i = 0; i < questionCount; i++) {
                SingleQuestion question = nextQuestion();
                showQuestion(question);
                allPlayers.get(0).setNewScore(question, 0, false); // CHANGE !!
            }
        }
        else {
            int correctAnswer = 0;
            while(correctAnswer<5) {
                nextQuestion();
            }
        }
    }

    public SingleQuestion nextQuestion() {
        String randomCategory = getRandomCategory();
        SingleQuestion question = allQuestions.getRandomQuestion(randomCategory);
        System.out.println("The next question is about: " + question.getCategory());
        return question;
    }

    public void getScore() {
        int score = 0;
        switch(this.round) {
            case(0):    score = 1000; break;
            case(1):     break;
            case(2):    /*score = BettingRound();*/break;
            case(3):    break;
            case(4):    break;
        }
    }

    public void printTotalScore() {
        System.out.println();
        System.out.println("Total score of:");
        for (Player allPlayer : allPlayers) {
            System.out.println("* " + allPlayer.getName() + " is " + allPlayer.getScore() + " points.");
            System.out.println();
            allPlayer.getQuestionsAndResults();
        }
    }
}