import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void getName() {
        Player p = new Player("Alex");
        assertEquals("Alex",p.getName());
    }

    @Test
    public void setName() {
        Player p = new Player("Alex");
        p.setName("Kostas");
        assertEquals("Kostas",p.getName());
    }

    @Test
    public void getScore() {
        Player p = new Player("Alex");
        assertEquals(0,p.getScore(),0.0001); // perimenei 0 epeidh to paixnidi den exei 3ekinhsei
    }

    @Test
    public void setNewScore() { // den 3erw ti na kanw edw!!!!

        /*
        SingleQuestion sq = new SingleQuestion("Question?");
        Player p = new Player("Alex");
        p.setNewScore(sq,1000,2);
        assertEquals(1000,p.getScore());

         */
    }

    @Test
    public void getAnswer() { //idea den exw tupwnei aplws to println!!!
        Player p = new Player("Alex");
        assertEquals(-1,p.getAnswer());

    }

    @Test
    public void getQuestionsAndResults() {
    }
}