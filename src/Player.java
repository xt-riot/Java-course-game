// imports

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

class answeredQuestions {
    private HashMap<SingleQuestion, String> questions;

    public answeredQuestions(int rounds) {
        questions = new HashMap<>(rounds);
    }

    public void addNewAnsweredQuestion(SingleQuestion question, String answer) {
            this.questions.put(question, answer);
    }

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

// Player Class
public class Player {
    private String playerName;
    private int score;
    private answeredQuestions questionsAndResult;

    public Player(String name, int rounds) {
        this.playerName = name;
        this.score = 0;
        questionsAndResult = new answeredQuestions(rounds);
    }

    public void setName(String name) {
        this.playerName = name;
    }
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
