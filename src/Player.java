// imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 *Δημιουργούμε ενα hashmap<key,values> όπου σε κάθε μοναδικό key (SingleQuestion) αντιχτοιχή κάθε μοναδική ερώτηση και
 *σαν value (String) παίρνουμε την ερώτηση.
 */
class answeredQuestions {
    private HashMap<SingleQuestion, String> questions;

    /**
     *
     * @param rounds
     */
    public answeredQuestions(int rounds) {
        questions = new HashMap<>(rounds);
    }

    public void addNewAnsweredQuestion(SingleQuestion question, String answer) {
        this.questions.put(question, answer);
    }

    /**
     *Στην μέθοδο printAnswers η οποία δεν επιστρέφει τιμή, τυπώνει τις ερωτήσεις χρησιμοιόντας μια for με βάση το μοναδικό τους key (k).
     */
    public void printAnswers() {
        Iterator<SingleQuestion> k = questions.keySet().iterator();
        for(int j =0; j<questions.size(); j++) {
            SingleQuestion temp = k.next();
            System.out.print("Question: " + temp.getQuestion());
            System.out.print("\t\t\tAnswered: " + questions.get(temp));
            System.out.println("\tCorrect answer was: " + temp.getAnswers().get(temp.getAnswers().size()-1));
        }
    }
}

/**
 *Με την κλάση Player δημιουργούμε έναν παίκτη όπου κρατάμε το σκορ και τις απαντήσεις του.
 */
public class Player {
    private String playerName;
    private int score;
    private answeredQuestions questionsAndResult;

    /**
     * Ο κατασκευαστής της κλάσης.
     * @param name Το όνομα του χρήστη.
     * @param rounds Ο αριθμός των γύρων.
     */

    public Player(String name, int rounds) {
        this.playerName = name;
        this.score = 0;
        questionsAndResult = new answeredQuestions(rounds);
    }

    public void setName(String name) {
        this.playerName = name;
    }

    /**
     * Στην μέθοδο setNewScore η οποία δεν επιστρέφει τιμή, ελέγχουμε άμα η απάντησή του είναι
     * σωστή και ενημερώνουμε την βαθμολογία του.
     * @param question Η απάντηση που έδωσε ο χρήστης.
     * @param newScore Το σκορ το οποίο ενημερώνεται με βάση την απάντηση του παίκτη.
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
     * Στην μέθοδο getAnswer η οποία επιστρέφει ακέραιο αριθμό, δημιουργούμε ένα αντικείμενο
     * με το όνομα userInput.
     * @return Επιστρέφει την απάντηση.
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
