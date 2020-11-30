// imports

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AllQuestions {
    private HashMap<String, ArrayList<SingleQuestion>> questions;

    public AllQuestions() {
        questions = new HashMap<>();
    }

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

    private void newEntry(String category, SingleQuestion entry) {

        if(questions.containsKey(category))
            questions.get(category).add(entry);
        else {
            ArrayList<SingleQuestion> temp= new ArrayList<>();
            temp.add(entry);
            questions.put(category, temp);
        }
    }
    public SingleQuestion getRandomQuestion(String category) {
        Random randomNumber = new Random();
        ArrayList<SingleQuestion> randomQuestionCategory = this.questions.get(category);
        SingleQuestion randomQuestion = randomQuestionCategory.get(randomNumber.nextInt(randomQuestionCategory.size()));
        questions.get(category).remove(randomQuestion);
        return randomQuestion;
    }
    public String[] getAllCategories() {
        String[] allCategories = new String[questions.size()];
        Iterator<String> i = questions.keySet().iterator();
        for(int j=0; j<allCategories.length; j++) {
            allCategories[j] = (i.next());
        }
        return allCategories;
    }
    public int getTotalQuestions() {
        int count = 0;
        Iterator<String> i = questions.keySet().iterator();
        for(int j = 0; j < questions.size(); j++) {
            count += questions.get(i.next()).size();
        }
        return count;
    }

    public ArrayList<SingleQuestion> getCategoryQuestions(String category) {
        return questions.get(category);
    }
}
