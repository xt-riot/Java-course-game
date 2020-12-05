// imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 *  <pre>Βοηθητική κλάση answeredQuestions.
 *
 *  Χρησιμοποιείται για την αποθήκευση των απαντημένων ερωτήσεων από τον κάθε παίχτη.</pre>
 */
class answeredQuestions {
    private HashMap<SingleQuestion, String> questions;

    /**
     *  answeredQuestions constructor
     */
    public answeredQuestions() {
        questions = new HashMap<>();
    }

    /**
     * Εισαγωγή νέας απαντημένης ερώτησης.
     * @param question Η ερώτηση σε μορφή SingleQuestion object
     * @param answer Η απάντηση του παίχτη
     */
    public void addNewAnsweredQuestion(SingleQuestion question, String answer) {
        this.questions.put(question, answer);
    }

    /**
     *  <pre>Εκτύπωση όλων των ερωτήσεων καθώς και των απαντήσεων που έχει δώσει ο παίχτης.
     *  Κάθε εκτύπωση είναι της μορφής:
     *
     *  Ερώτηση
     *      Απάντηση του χρήστη
     *          Σωστή απάντηση</pre>
     */
    public void printAnswers() {
        Iterator<SingleQuestion> k = questions.keySet().iterator();
        for(int j =0; j<questions.size(); j++) {
            SingleQuestion temp = k.next();
            System.out.print("Question: " + temp.getQuestion());
            System.out.print("\tAnswered: " + questions.get(temp));
            System.out.println("\t\tCorrect answer was: " + temp.getAnswers().get(temp.getAnswers().size()-1));
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
     * @param name Το όνομα του παίχτη
     * @param rounds Οι συνολικοί γύροι παιχνιδιού(αποφασίζεται τυχαία στην αρχή του παιχνιδιού)
     */

    public Player(String name, int rounds) {
        this.playerName = name;
        this.score = 0;
        questionsAndResult = new answeredQuestions();
    }


    public void setName(String name) {
        this.playerName = name;
    }

    /**
     * Αλλαγή των πόντων του παίχτη
     * @param question SingleQuestion object - η ερώτηση που δόθηκε στον παίχτη
     * @param newScore Integer - Οι πόντοι που θα προστεθούν στους συνολικούς πόντους του παίχτη
     */
    public void setNewScore(SingleQuestion question, int newScore) {
        int temp = getAnswer();
        if(question.correctAnswer().equals(question.getAnswers().get(temp))) {
            System.out.println("CORRECT!!");
            this.score += newScore;
        }
        //System.out.println(question.correctAnswer());
        questionsAndResult.addNewAnsweredQuestion(question, question.getAnswers().get(temp));
    }

    public String getName() {
        return this.playerName;
    }
    public int getScore() {
        return this.score;
    }
    public void getAnsweredQuestions() {
        questionsAndResult.printAnswers();
    }

    /**
     * <pre>Δέχεται την απάντηση του παίχτη σε κάποια ερώτηση.
     *
     * TODO Μη αποδοχή λάθος επιλογής(σε περίπτωση που ο χρήστης επιστρέψει μη-έγκυρη επιλογή)
     * </pre>
     * @return την απάντηση σε μορφή Integer(η επιλογή Α/a επιστρέφει την τιμή 0, η Β/b την τιμή 1 κ.ο.κ)
     *
     */
    public int getAnswer() {
        Scanner userInput = new Scanner(System.in);
        System.out.println("What is your answer? ");

        String answer = userInput.nextLine();
        int temp = -1;
        switch(answer) {
            case("A"): temp = 0; break;
            case("a"): temp = 0; break;
            case("B"): temp = 1; break;
            case("b"): temp = 1; break;
            case("C"): temp = 2; break;
            case("c"): temp = 2; break;
            case("D"): temp = 3; break;
            case("d"): temp = 3; break;
        }
        return temp;
    }
}
