import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AllQuestions {
    private HashMap<String, ArrayList<SingleQuestion>> questions;

    /**
     * Ο κατασκευαστής της κλάσης.
     */
    public AllQuestions() {
        questions = new HashMap<>();
    }

    /**
     * Στην μέθοδο fillQuestions η οποία δεν επιστρέφει τιμή, φορτώνουμε τις ερωτήσεις απο το .csv αρχείο. Όσο η πρώτη γραμμή δεν είναι null,
     * με την fileReader διαβάζουμε τους χαρακτήρες και με την BufferReader διαβάζουμε το κείμενο που περιέχει αυτούς τους χαρακτήρες.
     */
    public void fillQuestions() {
        String line = "";
        String fileDir = System.getProperty("user.dir") + "/QuizQuestions.csv";
        if( !(new File(fileDir).exists()) ) {
            fileDir = System.getProperty("user.dir") + "/src/QuizQuestions.csv";
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileDir));
            br.readLine();                          // Προσπέρασε την πρώτη γραμμή του αρχείου
            while ((line = br.readLine()) != null) {
                newEntry(new SingleQuestion(line));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Στην μέθοδο newEntry η οποία δεν επιστρέφει κάποια τιμή (void),
     * @param entry
     */
    private void newEntry(SingleQuestion entry) {

        if(questions.containsKey(entry.getCategory()))
            questions.get(entry.getCategory()).add(entry);
        else {
            ArrayList<SingleQuestion> temp= new ArrayList<>();
            temp.add(entry);
            questions.put(entry.getCategory(), temp);
        }
    }

    /**
     *Μέθοδος που επιλέγει τυχαία τις ερωτήσεις.
     * @param category Δέχεται την κατηφορία της ερώτησης (String).
     * @return Επιστρέφει την τυχαία ερώτηση.
     */
    public SingleQuestion getRandomQuestion(String category) {
        ArrayList<SingleQuestion> randomQuestionCategory = this.questions.get(category);
        Collections.shuffle(randomQuestionCategory);
        SingleQuestion randomQuestion = randomQuestionCategory.get(0);
        questions.get(category).remove(randomQuestion);
        return randomQuestion;
    }

    /**
     * Μέθοδος για να πάρουμε όλες τις κατηγορίες.
     * @return Επιστρέφει όλες τις κατηγορίες που υπάρχουν στο .csv αρχείο.
     */
    public String[] getAllCategories() {
        String[] allCategories = new String[questions.size()];
        Iterator<String> i = questions.keySet().iterator();
        for(int j=0; j<allCategories.length; j++) {
            allCategories[j] = (i.next());
        }
        return allCategories;
    }

    /**
     * Μέθοδος για τον υπολογισμό όλων των ερωτήσεων.
     * @return Επιστρέφει το σύνολο των ερωτήσεων.
     */
    public int getTotalQuestions() {
        int count = 0;
        Iterator<String> i = questions.keySet().iterator();
        for(int j = 0; j < questions.size(); j++) {
            count += questions.get(i.next()).size();
        }
        return count;
    }

    /**
     * Μέθοδος για να πάρουμε την κατηγορία.
     * @param category Δέχεται ένα String δηλαδή την κατηφορία της ερώτησης.
     * @return Επιστρέφει την κατηγορία της ερώτησης.
     */
    public ArrayList<SingleQuestion> getCategoryQuestions(String category) {
        return questions.get(category);
    }
}
