import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class StoredDataTest {

    @Test
    public void saveData() {
        File file = new File("C:\\Users\\Alexandros\\IdeaProjects\\asd\\storedData.csv");
        boolean expectedResult = true;
        boolean actualResult = false;
        if (file.exists()){
            actualResult = true;
        }
        assertEquals(expectedResult, actualResult);
    }
}