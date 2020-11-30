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

    public SingleQuestion(String question, ArrayList<String> answers, String category ) {
        // CODE to get question from a file(path) in the designated line(questionNumber)


        this.question = question;
        this.answers = answers;
        this.hasTheQuestionImage = false;
        this.category = category;
    }

    public boolean getHasTheQuestionImage() {
        return this.hasTheQuestionImage;
    }
    public ArrayList<String> getAnswers() {
        return this.answers;
    }
    public String getQuestion() {
        return this.question;
    }
    public String getCategory() {
        return this.category;
    }
    public void printObject() {
        System.out.println("Category: " + this.category);
        System.out.println("Question: " + this.question);
        System.out.println("Answers: " + this.answers);
        System.out.println();
    }
    public String correctAnswer() {
        return this.answers.get(this.answers.size()-1);
    }
}
