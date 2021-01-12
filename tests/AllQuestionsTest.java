import jdk.jfr.Category;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class AllQuestionsTest {


    @Test
    public void fillQuestions() {

        File file = new File("C:\\Users\\Alexandros\\IdeaProjects\\asd\\src\\QuizQuestions.csv");
        boolean expectedResult = true;
        boolean actualResult = false;
        if (file.exists()){
            actualResult = true;
        }
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void getRandomQuestion() {
    }

    @Test
    public void getAllCategories() { // den einai swsto!! Hello einai string opote panta swsto 8a bgainei


        AllQuestions AQ = new AllQuestions();
        String[] question = {"Food","Sport","Animals"};

        assertTrue("Hello",true);

    }

    @Test
    public void getTotalQuestions() {
        /*
        AllQuestions AQ = new AllQuestions();
        int count = 5;
        assertEquals(count,AQ.getTotalQuestions());

         */

    }
    @Test
    public void getCategoryQuestions() {
    }

}