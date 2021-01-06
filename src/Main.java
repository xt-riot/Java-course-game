import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rant = new Random();
        int rounds = rant.nextInt(2) + 1;
        int questions = rant.nextInt(2) + 1;
        GUI gui = new GUI();
        GameEnvironment game = new GameEnvironment(1, 2, questions, gui);
        //game.startGame();

        gui.start();
        gui.choosePlayers(game);
        //
        /*
        int players = getPlayers(gui);
        //
        System.out.println("Hello");
        gui.startGame(game);
        /*
        System.out.println("You will play: " + rounds + " rounds.");
        System.out.println("Each round will consist of " + questions + " questions.");
        GameEnvironment game = new GameEnvironment(rounds, questions, 1);
        for (int i = 0; i < rounds; i++)
            game.nextRound();
        game.printTotalScore();
    */}

}
