import java.util.Random;

public class Main {
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.start();
        int players = gui.choosePlayers();
        /*
        Random rant = new Random();
        int rounds = rant.nextInt(5) + 1;
        int questions = rant.nextInt(5) + 1;
        System.out.println("You will play: " + rounds + " rounds.");
        System.out.println("Each round will consist of " + questions + " questions.");
        GameEnvironment game = new GameEnvironment(rounds, questions, 1);
        for (int i = 0; i < rounds; i++)
            game.nextRound();
        game.printTotalScore();
    */}
}
