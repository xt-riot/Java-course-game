import java.util.Scanner;
import java.util.HashMap;
import java.util.Iterator;

/**
 * <pre>Βοηθητική κλάση answeredQuestions.
 *
 *  Χρησιμοποιείται για την αποθήκευση των απαντημένων ερωτήσεων από τον κάθε παίχτη.
 *  TODO Μέθοδο για την εκτύπωση συγκεκριμένης απαντημένης ερώτησης.
 *  </pre>
 */
class answeredQuestions {
    private HashMap<SingleQuestion, String> questions;

    /**
     * answeredQuestions constructor
     */
    public answeredQuestions() {
        questions = new HashMap<>();
    }

    /**
     * Εισαγωγή νέας απαντημένης ερώτησης.
     *
     * @param question Η ερώτηση σε μορφή SingleQuestion object
     * @param answer   Η απάντηση του παίχτη
     */
    public void addNewAnsweredQuestion(SingleQuestion question, String answer) {
        this.questions.put(question, answer);
    }

    /**
     * <pre>Εκτύπωση όλων των ερωτήσεων καθώς και των απαντήσεων που έχει δώσει ο παίχτης.
     *  Κάθε εκτύπωση είναι της μορφής:
     *
     *  Ερώτηση
     *      Απάντηση του χρήστη
     *          Σωστή απάντηση</pre>
     */
    public void printAnswers() {
        Iterator<SingleQuestion> k = questions.keySet().iterator();
        for (int j = 0; j < questions.size(); j++) {
            SingleQuestion temp = k.next();
            System.out.print("Question: " + temp.getQuestion());
            System.out.print("\n\tAnswered: " + questions.get(temp));
            System.out.println("\n\t\tCorrect answer was: " + temp.getAnswers().get(temp.getAnswers().size() - 1));
        }
    }
}

/**
 * <pre>Κλάση Player
 *
 * Αντικείμενο με το οποίο αναπαριστάται κάθε παίχτης.
 * Παρέχει πληροφορίες για:
 *      * Το όνομα του παίχτη,
 *      * Τους πόντους του παίχτη,
 *      * Τις απαντημένες ερωτήσεις του παίχτη.</pre>
 */
public class Player {
    private String playerName;
    private int score;
    private answeredQuestions questionsAndResult;

    /**
     * Player constructor
     *
     * @param name Το όνομα του παίχτη
     */

    public Player(String name) {
        this.playerName = name;
        this.score = 0;
        questionsAndResult = new answeredQuestions();
    }

    public String getName() {
        return this.playerName;
    }

    public void setName(String name) {
        this.playerName = name;
    }

    public int getScore() {
        return this.score;
    }

    /**
     * Αλλαγή των πόντων του παίχτη
     *
     * @param question SingleQuestion object - η ερώτηση που δόθηκε στον παίχτη
     * @param newScore Integer - Οι πόντοι που θα προστεθούν στους συνολικούς πόντους του παίχτη
     */
    public void setNewScore(SingleQuestion question, int newScore, int answer) {
        //int lastAnswer = this.getAnswer();
        String tempString = "You are incorrect. The correct answer is: " + question.correctAnswer() + "\n\n";
        if (question.correctAnswer().equals(question.getAnswers().get(answer))) {
            this.score += newScore;
            tempString = "You are correct!\n\n";
        }
        System.out.println(tempString);
        questionsAndResult.addNewAnsweredQuestion(question, question.getAnswers().get(answer));
    }

    /**
     * <pre>Δέχεται την απάντηση του παίχτη σε κάποια ερώτηση.
     *
     * TODO Μη αποδοχή λάθος επιλογής(σε περίπτωση που ο χρήστης επιστρέψει μη-έγκυρη επιλογή)
     * </pre>
     */
    protected int getAnswer() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is your answer? ");

        String answer = userInput.nextLine();
        answer = answer.toUpperCase();
        int lastAnswer = -1;
        switch (answer) {
            case ("A"):
                lastAnswer = 0;
                break;
            case ("B"):
                lastAnswer = 1;
                break;
            case ("C"):
                lastAnswer = 2;
                break;
            case ("D"):
                lastAnswer = 3;
                break;
        }
        return lastAnswer;
    }

    public void getQuestionsAndResults() {
        questionsAndResult.printAnswers();
    }
}
