
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class StoredData {
    private String playerName;
    private ArrayList<String> answers;
    private String category;



    public StoredData(String filename) {


        try {
            Scanner reader = new Scanner(new File("out/production/Buzz/QuizQuestions.csv"));
            PrintWriter writer = new PrintWriter(new File("storedData.csv"));
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(",");
                this.answers = new ArrayList<>();


                this.category = data[0];
                //name = data[1];
                answers.add(data[1]);

                writer.println(data[0] + ", " +  data[1]);
            }
            writer.close();
            reader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }



    }

    public String getCategory() {
        return this.category;
    }

    public String correctAnswer() {
        return this.answers.get(this.answers.size() - 1);
    }

    public String getName() {
        return this.playerName;
    }

}
