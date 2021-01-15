import java.util.ArrayList;
import java.util.Random;

/**
 * Κλάση GameRounds που δημιουργεί την επιλογή στοιχήματος για τους γύρους.
 */
class GameRounds {
    private final ArrayList<String> gameRounds;
    private int score;
    private int roundIndex;

    /**
     * Κατασκευαστής της κλάσης GameRounds.
     */
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

    /**
     * Μέθοδος για το όνομα κάθε γύρου.
     * @return όνομα γύρου
     */
    public String getRoundName() {
        return this.gameRounds.get(roundIndex);
    }

    /**
     * Μέθοδος για τις πληροφορίες κάθε γύρου.
     * @return πληροφορίες γύρου
     */
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
                asd += ("Fast answer: the first player to answer correctly earns 1000 points ");
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

    /**
     * Μέθοδος που δείχνει τις επιλογες στοιχήματος.
     * @return την τιμή που στοιχηματίστικε.
     */
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

    /**
     * Μέθοδος για τυχαίο γύρο παιχνιδιού.
     */
    public void RandomRound() {
        Random temp = new Random();
        this.roundIndex = temp.nextInt(gameRounds.size());
    }

    /**
     * Μέθοδος που παίρνει τον σκορ.
     * @return τιμή του σκορ
     */
    public int getScore() {
        return switch (this.roundIndex) {
            case (0) -> 1000;
            case (1) -> 1000;
            case (2) -> BettingRound();
            case (3) -> 1000;
            case (4) -> 1000;
            default -> 0;
        };
    }

    /**
     * Μέθοδος για τους γύρους στοιχηματος.
     * @return την τιμή που στοιχηματίστικε.
     */
    public boolean isBettingRound() {
        return this.roundIndex == 2;
    }
}

