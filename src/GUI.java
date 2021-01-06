import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.logging.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private static Logger dbg = Logger.getLogger(GUI.class.getName());
    private GameEnvironment game;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel botPanel;

    private JPanel[][] panels;
    private JLabel[][] labels;
    private JButton[][] answerButtons;

    private JLabel label;
    private JLabel label1;
    private JLabel label2;

    private int players;
    private ArrayList<Player> allPlayers;
    private int[] answers;

    public GUI() {

        players = 0;
        // frame initialization
        frame = new JFrame("Buzz! Quiz World ripoff");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.RED);
        frame.setSize(800, 400);

        label = new JLabel("asd");

        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        topPanel.setBackground(Color.ORANGE);
        topPanel.setPreferredSize(new Dimension(800, 50));


        frame.add(topPanel, BorderLayout.PAGE_START);


        midPanel = new JPanel();
        midPanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        midPanel.setBackground(Color.WHITE);
        midPanel.setPreferredSize(new Dimension(800, 300));

        frame.add(midPanel);
        label1 = new JLabel("asd");


        botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        botPanel.setBackground(Color.BLACK);
        botPanel.setPreferredSize(new Dimension(800, 50));

        frame.add(botPanel, BorderLayout.PAGE_END);
        label2 = new JLabel("bot");
        botPanel.add(label2, BorderLayout.CENTER);


        frame.setLocationRelativeTo(null);
        panels = new JPanel[3][];
        labels = new JLabel[3][];

    }

    private void test() {
        panels[0] = new JPanel[players+1];
        panels[0][0] = topPanel;
        panels[1] = new JPanel[players+1];
        panels[1][0] = midPanel;
        panels[1][0].setLayout(new FlowLayout(FlowLayout.LEADING, 0, 10));
        panels[2] = new JPanel[players+1];
        panels[2][0] = botPanel;
        Color[] tempcolor = {Color.GREEN, Color.RED};
        String[] temp = {BorderLayout.PAGE_START, BorderLayout.CENTER, BorderLayout.PAGE_END};
        labels[0] = new JLabel[3];
        labels[0][0] = label;

        labels[1] = new JLabel[3];
        labels[1][0] = label1;

        labels[2] = new JLabel[3];
        labels[2][0] = label2;

        System.out.println(labels[0].length);
        for(int j = 0; j<3; j++) {
            for ( int i = 1; i <= players; i++) {
                panels[j][i] = new JPanel();
                panels[j][i].setLayout(new FlowLayout(FlowLayout.LEADING));
                panels[j][i].setBackground(tempcolor[i-1]);
                panels[j][i].setPreferredSize(new Dimension(panels[j][0].getSize().width/players, panels[j][0].getSize().height));
                panels[j][0].add(panels[j][i]);
                labels[j][i] = new JLabel(allPlayers.get(i-1).getName() +  " panel.");
                panels[j][i].add(labels[j][i]);

                if(j == 1) {
                    answerButtons[i-1] = new JButton[4];
                    for(int k = 0; k<4; k++) {

                        int p = k;
                        int o = i-1;
                        answerButtons[i-1][k] = new JButton();
                        answerButtons[i-1][k].setPreferredSize(new Dimension(100, 20));
                        answerButtons[i-1][k].addActionListener(actionEvent -> {
                            answers[o] = p;
                            System.out.println(p);
                            answersReady();
                        });
                        panels[j][i].add(answerButtons[i-1][k]);
                    }
                }
            }
            frame.add(panels[j][0], temp[j]);
        }
        frame.repaint();
    }

    public void choosePlayers(GameEnvironment id) {
        this.game = id;
        label.setText("Welcome to Buizz! Quiz World rip-off.\nPlease choose the number of players:");
        topPanel.add(label, BorderLayout.CENTER);

        JButton player1 = new JButton("One player");
        player1.setPreferredSize(new Dimension(200, 100));
        player1.addActionListener(actionEvent -> {
            if(players!=1) {
                players = 1;
                updatePlayers();
            }
        });
        JButton player2 = new JButton("Two players");
        player2.setPreferredSize(new Dimension(200, 100));
        player2.addActionListener(actionEvent -> {
            if(players!=2) {
                players = 2;
                updatePlayers();
            }
        });
        midPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 133, 80));
        midPanel.add(player1, BorderLayout.CENTER);
        midPanel.add(player2, BorderLayout.CENTER);
    }

    private void updatePlayers() {
        for(Component comp : botPanel.getComponents())
            if(comp instanceof JButton)
                botPanel.remove(comp);
        botPanel.repaint();
        if(players==1) {
            label2.setText("You chose one player.");
        }
        else {
            label2.setText("You chose two players.");
        }
        answerButtons = new JButton[players][];
        panels = new JPanel[3][players];
        labels = new JLabel[3][players];
        answers = new int[players];
        for(int i = 0; i< players; i++) {
            answers[i] = -1;
        }
        label2.setForeground(Color.RED);


        JButton next = new JButton("Continue.");
        next.setPreferredSize(new Dimension(100, 20));
        next.addActionListener(actionEvent -> {
            label.setText("Buzz! Quiz world is about to begin with: " + players + " player(s).");
            label.setForeground(Color.CYAN);
            try {
                Thread.sleep(1000);
                dbg.warning("Wiping");
                wipeAll();
                setFrameVisible(false);
                choosePlayerName();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        botPanel.add(next, BorderLayout.LINE_END);
    }

    public void choosePlayerName() {
        allPlayers = new ArrayList<>();
        for(int i = 1; i<=players; i++) {
            String name = JOptionPane.showInputDialog(frame, "Choose the name of player"+i, "Player"+i+" name", JOptionPane.QUESTION_MESSAGE);
            if (name.length() > 0)
                allPlayers.add(new Player(name));
            else
                allPlayers.add(new Player("Player"+i));
        }
        test();
        setFrameVisible(true);
        startG();
    }

    public void wipeAll() {
        topPanel.removeAll();
        midPanel.removeAll();
        botPanel.removeAll();
        frame.repaint();
    }
    /**
     * setVisible when you are ready
     */
    public void setFrameVisible(boolean set) {
        frame.setVisible(set);
    }

    public void prepare(SingleQuestion[] question) {

        for(int i = 1; i<= players; i++) {
            labels[0][i].setText(allPlayers.get(i-1).getName() + " panel\n Category: \n" + question[i-1].getCategory());
            labels[1][i].setText("Question: " + question[i-1].getQuestion());
            labels[2][i].setText(allPlayers.get(i-1).getName() + " panel");
            for(int k = 0; k<4; k++)
                answerButtons[i-1][k].setText(question[i-1].getAnswers().get(k));
        }
    }


    private void answersReady() {
        boolean send = false;
        for(int i = 0; i<allPlayers.size(); i++) {
            send = answers[i] != -1;
        }
        if(send) {
            game.ready(answers);
            for(int i = 0; i<allPlayers.size(); i++)
                answers[i] = -1;
        }
    }
    public void next() {
        game.newQuestion();
    }

    private void startG() {
        this.game.setPlayers(allPlayers);
        this.game.startGame();
    }

    public void endOfGame() {
        labels[0][0].setText("End of game");
        panels[0][0].removeAll();
        panels[0][0].add(labels[0][0]);
        for(int i = 1; i<=players; i++) {
            labels[0][i].setText(game.printTotalScore(i-1));
            panels[1][i].removeAll();
            panels[1][0].add(panels[1][i]);
            panels[1][i].add(labels[0][i]);
        }
        frame.repaint();
    }
}