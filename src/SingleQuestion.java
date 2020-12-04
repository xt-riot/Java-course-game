// imports
import java.util.ArrayList;
import java.util.Scanner;

public class SingleQuestion {
    private String question;
    private ArrayList<String> answers;

    /*private String firstAnswer;
    private String secondAnswer;
    private String thirdString;
    private String forthString;
    private String correctAnswer;*/

    private boolean hasTheQuestionImage;
    private String category;

    /**
     * Ο κατασκευαστής της κλάσης.
     * @param question Οι ερωτήσεις του παιχνιδίου.
     * @param answers Οι απάντησείς του χρήστη.
     * @param category Οι κατηγορίες των ερωτήσεων απο το .csv αρχείο.
     */
    public SingleQuestion(String question, ArrayList<String> answers, String category ) {
        // CODE to get question from a file(path) in the designated line(questionNumber)


        this.question = question;
        this.answers = answers;
        this.hasTheQuestionImage = false;
        this.category = category;
    }

    /**
     * Για αργότερα θα το χρησιμοποιήσουμε.
     * @return
     */
    public boolean getHasTheQuestionImage() {
        return this.hasTheQuestionImage;
    }

    /**
     * Μία λίστα απο Strings που περιέχει τις απαντήσεις.
     * @return Επιστρέφει την απάντηση.
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * Επιλέγει την ερώτηση.
     * @return Επιστρέφει την ερώτηση που επιλέχτηκε.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Επιλέγει την κατηγορία.
     * @return Επιστρέφει την κατηφορία που επιλέχτηκε.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Τυπώνει το αντικείμενο της κλάσης και με τις παραμέτρους της.
     */
    public void printObject() {
        System.out.println("Category: " + this.category);
        System.out.println("Question: " + this.question);
        System.out.println("Answers: " + this.answers);
        System.out.println();
    }

    /**
     * Μέθοδος για την σωστή απάντηση.
     * @return Επιστρέφει την σωστή απάντηση.
     */
    public String correctAnswer() {
        return this.answers.get(this.answers.size()-1);
    }
}
