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
     * Δηλώνουμε τι περιέχουν το ArrayList με βάση τον αρχείο .CSV.
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
        //Collections.shuffle(answers);               // Ανακάτεψε τις απαντήσεις πριν μπει η σωστή
        answers.add(data[6]);                       // Το έβδομο κελί   - Σωστή απάντηση
    }

    /**
     * Μέθοδος που θα χρησιμοποιηθεί αργότερα.
     *
     * @return Επιστρέφει την εικόνα.
     */
    public boolean getHasTheQuestionImage() {
        return this.hasTheQuestionImage;
    }

    /**
     * Μέθοδος για τις απαντήσεις.
     *
     * @return Επιστρέφει την απάντηση.
     */
    public ArrayList<String> getAnswers() {
        return this.answers;
    }

    /**
     * Μέθοδος που επιλέγει την ερώτηση απο το αρχείο.
     *
     * @return Επιστρέφει την ερώτηση που επιλέχτηκε.
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * Μέθοδος που επιλέγει την κατηγορία.
     *
     * @return Επιστρέφει την κατηφορία που επιλέχτηκε.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Μέθοδος που τυπώνει το αντικείμενο της κλάσης και με τις παραμέτρους της.
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