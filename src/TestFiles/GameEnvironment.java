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

        return roundIndex;
    }

    public String getRoundText(int round) {
        String asd = "";
        switch(roundIndex) {
            case(0):
                asd += ("Round: Correct answer.");
                asd += ("If you guess the correct answer, you will get 1000 points.");
                //System.out.println();
                break;
            case(1):
                asd += ("Round: Stop the counter.");
                asd += ("There is a counter. Every player that answers correctly");
                asd += ("earns points proportional to the time left in the counter.");
                break;
            case(2):
                asd += ("Round: Betting.");
                asd += ("If you guess the correct answer, you will get the bet points,");
                asd += ("otherwise you will lose them.");
                //System.out.println();
                break;
            case(3):
                asd += ("Round: Fast answer.");
                asd += ("The first player to answer correctly earns 1000 points");
                asd += ("while the second 500.");
                break;
            case(4):
                asd += ("Round: Thermometer:");
                asd += ("The first player to answer 5 questions correctly");
                asd += ("earns 5000 points.");
                break;
        }
        return asd;
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
    private GUI gui;
    private SingleQuestion questions[];
    private ArrayList<String> categories;
    private ArrayList<Player> allPlayers;
    private AllQuestions allQuestions;

    public GameEnvironment(int rounds, int noQuestions) {
        allQuestions = new AllQuestions();
        allQuestions.fillQuestions();

        roundCount = rounds;
        questionCount = noQuestions;
        playerCount = -1;
        this.questions = new SingleQuestion[2];

        categories = new ArrayList<>();
        String[] temp = allQuestions.getAllCategories();
        for (String s : temp) {
            if (allQuestions.getCategoryQuestions(s).size() >= questionCount) {
                categories.add(s);
            }
        }
        allPlayers = new ArrayList<>();
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

        while(!(allQuestions.getCategoryQuestions(categories.get(random)).size() > 0))
            random = temp.nextInt( categories.size() );
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

    public String printTotalScore(int number) {
        System.out.println();
        System.out.println("Total score of:");
        for (Player allPlayer : allPlayers) {
            System.out.println("* " + allPlayer.getName() + " is " + allPlayer.getScore() + " points.");
            System.out.println();
            allPlayer.getQuestionsAndResults();
        }
        return "Total score of player"+number+": " + allPlayers.get(number).getScore();
    }

    public void setGUI(GUI id) {
        this.gui = id;
    }
    public void setPlayers(ArrayList<Player> p) {
        allPlayers = p;
        playerCount = allPlayers.size();
    }
    public void startGame() {
        this.round = new GameRounds().RandomRound();
        for(int i = 0; i<playerCount; i++)
            questions[i] = nextQuestion();
        gui.prepare(questions);
    }

    public void ready(int[] answer) {
        for(int i = 0; i<playerCount; i++) {
            allPlayers.get(i).setNewScore(questions[i], 1000, answer[i]);
        }
        gui.next();
    }

    public void newRound() {
        this.roundCount--;
        this.round = new GameRounds().RandomRound();
        for(int i = 0; i<playerCount; i++)
            questions[i] = nextQuestion();
        gui.prepare(questions);
    }
    public void newQuestion() {
        this.questionCount--;
        if(questionCount > 0) {
            for(int i = 0; i<playerCount; i++)
                questions[i] = nextQuestion();
            gui.prepare(questions);
        } else if(roundCount > 0) {
            this.round = new GameRounds().RandomRound();
            newRound();
        }
        else {
            gui.endOfGame();
        }
    }
}