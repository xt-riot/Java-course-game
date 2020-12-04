// imports

import java.io.BufferedReader;
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
     * Στην μέθοδο fillQuestions η οποία δεν επιστρέφει τιμή, φορτώνουμε τις ερωτήσεις απο το .csv αρχείο. Όσο το αρχείο δεν είναι null,
     * με την fileReader διαβάζουμε τους χαρακτήρες και με την BufferReader διαβάζουμε το κείμενο που περιέχει αυτούς τους χαρακτήρες,
     * ξεκινάμε απο το 3ο κελί καθώς στο πρώτο κελί έχουμε το key, δεύτερο κελί έχουμε τις κατηγορίες ερωτήσεων.
     */
    public void fillQuestions() {
        String line = "";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/QuizQuestions.csv"));
            br.readLine();
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                String[] data = line.split(splitBy);    // use comma as separator
                ArrayList<String> answers = new ArrayList<>();
                answers.add(data[2]);
                answers.add(data[3]);
                answers.add(data[4]);
                answers.add(data[5]);
                answers.add(data[6]);
                SingleQuestion question = new SingleQuestion(data[1], answers, data[0]);
                newEntry(data[0], question);

            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Στην μέθοδο newEntry η οποία δεν επιστρέφει κάποια τιμή (void),
     * @param category
     * @param entry
     */
    private void newEntry(String category, SingleQuestion entry) {

        if(questions.containsKey(category))
            questions.get(category).add(entry);
        else {
            ArrayList<SingleQuestion> temp= new ArrayList<>();
            temp.add(entry);
            questions.put(category, temp);
        }
    }

    /**Στην μέθοδο getRandomQuestion η οποία επιστρέφει ένα String,
     *
     * @param category
     * @return
     */
    public SingleQuestion getRandomQuestion(String category) {
        Random randomNumber = new Random();
        ArrayList<SingleQuestion> randomQuestionCategory = this.questions.get(category);
        SingleQuestion randomQuestion = randomQuestionCategory.get(randomNumber.nextInt(randomQuestionCategory.size()));
        questions.get(category).remove(randomQuestion);
        return randomQuestion;
    }

    /**
     * Στην μέθοδο getAllCategories η οποία επιστρέφει έναν πίνακα απο Strings.
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
     * Στην μέθοδο getTotalQuestions η οποία επιστρέφει έναν ακέραιο αριθμό (count).
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
     *
     * @param category
     * @return
     */
    public ArrayList<SingleQuestion> getCategoryQuestions(String category) {
        return questions.get(category);
    }
}
