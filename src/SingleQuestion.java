// imports

import java.util.ArrayList;
import java.util.Collections;
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
     */
    public SingleQuestion(String line) {
        // CODE to get question from a file(path) in the designated line(questionNumber)

        this.hasTheQuestionImage = false;
        this.answers = new ArrayList<>();
        String[] data = line.split(",");      // Σπάσε την γραμμή κάθε φορά που εμφανίζεται κόμα
        this.category = data[0];                    // Το πρώτο κελί    - Κατηγορία ερώτησης
        this.question = data[1];                    // Το δεύτερο κελί  - Ερώτηση
        answers.add(data[2]);                       // Το τρίτο κελί    - Απάντηση 1
        answers.add(data[3]);                       // Το τέταρτο κελί  - Απάντηση 2
        answers.add(data[4]);                       // Το πέμπτο κελί   - Απάντηση 3
        answers.add(data[5]);                       // Το έκτο κελί     - Απάντηση 4
        Collections.shuffle(answers);               // Ανακάτεψε τις απαντήσεις πριν μπει η σωστή
        answers.add(data[6]);                       // Το έβδομο κελί   - Σωστή απάντηση
    }

    /**
     * Για αργότερα θα το χρησιμοποιήσουμε.
     *
     * @return
     */
    public boolean getHasTheQuestionImage() {
        return this.hasTheQuestionImage;
    }

    /**
     * Μία λίστα απο Strings που περιέχει τις απαντήσεις.
     *
     * @return Επιστρέφει την απάντηση.
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * Επιλέγει την ερώτηση.
     *
     * @return Επιστρέφει την ερώτηση που επιλέχτηκε.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Επιλέγει την κατηγορία.
     *
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
     *
     * @return Επιστρέφει την σωστή απάντηση.
     */
    public String correctAnswer() {
        return this.answers.get(this.answers.size() - 1);
    }
}
