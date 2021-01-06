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

    private JPanel pTopWest;
    private JPanel pTopEast;
    private JPanel pMidWest;
    private JPanel pMidEast;
    private JPanel pBotWest;
    private JPanel pBotEast;

    private JPanel[][] panels;
    private JLabel[][] labels;
    private JButton[][] answerButtons;

    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel lTopEast;
    private JLabel lTopWest;
    private JLabel lMidEast;
    private JLabel lMidWest;
    private JLabel lBotEast;
    private JLabel lBotWest;

    private JButton answerAplayerA;
    private JButton answerBplayerA;
    private JButton answerCplayerA;
    private JButton answerDplayerA;
    private JButton answerAplayerB;
    private JButton answerBplayerB;
    private JButton answerCplayerB;
    private JButton answerDplayerB;
    private int players;
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
        //midPanel.add(label1, BorderLayout.CENTER);


        botPanel = new JPanel();
        botPanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
        botPanel.setBackground(Color.BLACK);
        botPanel.setPreferredSize(new Dimension(800, 50));

        frame.add(botPanel, BorderLayout.PAGE_END);
        label2 = new JLabel("bot");
        botPanel.add(label2, BorderLayout.CENTER);


        frame.setLocationRelativeTo(null);
        answers = new int[2];
        answers[0] = -1;
        answers[1] = -1;

        answerAplayerA = new JButton();
        answerAplayerA.setPreferredSize(new Dimension(100, 20));
        answerAplayerA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[0] = 1;
                answersReady();
            }
        });
        answerBplayerA = new JButton();
        answerBplayerA.setPreferredSize(new Dimension(100, 20));
        answerBplayerA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[0] = 2;
                answersReady();
            }
        });
        answerCplayerA = new JButton();
        answerCplayerA.setPreferredSize(new Dimension(100, 20));
        answerCplayerA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[0] = 3;
                answersReady();
            }
        });
        answerDplayerA = new JButton();
        answerDplayerA.setPreferredSize(new Dimension(100, 20));
        answerDplayerA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[0] = 4;
                answersReady();
            }
        });

        answerAplayerB = new JButton();
        answerAplayerB.setPreferredSize(new Dimension(100, 20));
        answerAplayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[1] = 1;
                answersReady();
            }
        });
        answerBplayerB = new JButton();
        answerBplayerB.setPreferredSize(new Dimension(100, 20));
        answerBplayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[1] = 2;
                answersReady();
            }
        });
        answerCplayerB = new JButton();
        answerCplayerB.setPreferredSize(new Dimension(100, 20));
        answerCplayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[1] = 3;
                answersReady();
            }
        });
        answerDplayerB = new JButton();
        answerDplayerB.setPreferredSize(new Dimension(100, 20));
        answerDplayerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                answers[1] = 4;
                answersReady();
            }
        });
        panels = new JPanel[3][];
        labels = new JLabel[3][];

    }

    private void test() {
        //frame.removeAll();
        panels[0] = new JPanel[players+1];
        panels[0][0] = topPanel;
        panels[1] = new JPanel[players+1];
        panels[1][0] = midPanel;
        panels[1][0].setLayout(new FlowLayout(FlowLayout.LEADING, 00, 10));
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
            //panels[j] = new JPanel[players];
            //System.out.println(panels[j].length);
            for ( int i = 1; i <= players; i++) {
                panels[j][i] = new JPanel();
                panels[j][i].setLayout(new FlowLayout(FlowLayout.LEADING));
                panels[j][i].setBackground(tempcolor[i-1]);
                panels[j][i].setPreferredSize(new Dimension(panels[j][0].getSize().width/players, panels[j][0].getSize().height));
                panels[j][0].add(panels[j][i]);
                labels[j][i] = new JLabel("Player" + i + " panel.");
                panels[j][i].add(labels[j][i]);


                if(j == 1) {
                    answerButtons[i-1] = new JButton[4];
                    for(int k = 0; k<4; k++) {

                        int p = k;
                        int o = i-1;
                        answerButtons[i-1][k] = new JButton();
                        answerButtons[i-1][k].setPreferredSize(new Dimension(100, 20));
                        answerButtons[i-1][k].addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                answers[o] = p;
                                answersReady();
                            }
                        });
                        panels[j][i].add(answerButtons[i-1][k]);
                    }
                }


            }
            frame.add(panels[j][0], temp[j]);
        }
        /*panels[1][1].add(answerAplayerA, BorderLayout.LINE_END);
        panels[1][1].add(answerBplayerA, BorderLayout.LINE_END);
        panels[1][1].add(answerCplayerA, BorderLayout.LINE_END);
        panels[1][1].add(answerDplayerA, BorderLayout.LINE_END);

        if(players == 2) {

            panels[1][2].add(answerAplayerB, BorderLayout.LINE_END);
            panels[1][2].add(answerBplayerB, BorderLayout.LINE_END);
            panels[1][2].add(answerCplayerB, BorderLayout.LINE_END);
            panels[1][2].add(answerDplayerB, BorderLayout.LINE_END);
        }//*/
        frame.repaint();
    }

    public void choosePlayers(GameEnvironment id) {
        this.game = id;
        label.setText("Welcome to Buizz! Quiz World rip-off.\nPlease choose the number of players:");
        topPanel.add(label, BorderLayout.CENTER);


        JButton player1 = new JButton("One player");
        player1.setPreferredSize(new Dimension(200, 100));
        player1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(players==0) {
                    players = 1;
                    updatePlayers();
                }
            }
        });
        JButton player2 = new JButton("Two players");
        player2.setPreferredSize(new Dimension(200, 100));
        player2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(players==0) {
                    players = 2;
                    updatePlayers();
                }
            }
        });
        midPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 133, 80));
        midPanel.add(player1, BorderLayout.CENTER);
        midPanel.add(player2, BorderLayout.CENTER);
    }

    private void updatePlayers() {
        if(players==1) {
            label2.setText("You chose one player.");
        }
        else {
            label2.setText("You chose two players.");
        }
        answerButtons = new JButton[players][];
        panels = new JPanel[3][players];
        labels = new JLabel[3][players];
        label2.setForeground(Color.RED);


        JButton next = new JButton("Continue.");
        next.setPreferredSize(new Dimension(100, 20));
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                label.setText("Buzz! Quiz world is about to begin with: " + players + " player(s).");
                label.setForeground(Color.CYAN);
                try {
                    Thread.sleep(1000);
                    dbg.warning("Wiping");
                    wipeAll();
                    test();
                    startG();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        botPanel.add(next, BorderLayout.LINE_END);
    }

    public void choosePlayerName() {
        for(int i = 0; i<players; i++) {
            label.setText("Choose the name of player"+players+".");

        }
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
    public void start() {
        frame.setVisible(true);
    }

    public void prepare(SingleQuestion[] question) {

        for(int i = 1; i<= players; i++) {
            labels[0][i].setText("Player"+i+" panel\n Category: \n" + question[i-1].getCategory());
            labels[1][i].setText("Question: " + question[i-1].getQuestion());
            labels[2][i].setText("Player"+i+" panel");
            for(int k = 0; k<4; k++)
                answerButtons[i-1][k].setText(question[i-1].getAnswers().get(k));
        }

        /*answerAplayerA.setText(question[0].getAnswers().get(0));
        answerBplayerA.setText(question[0].getAnswers().get(1));
        answerCplayerA.setText(question[0].getAnswers().get(2));
        answerDplayerA.setText(question[0].getAnswers().get(3));

        if(players == 2) {
            answerAplayerB.setText(question[1].getAnswers().get(0));
            answerBplayerB.setText(question[1].getAnswers().get(1));
            answerCplayerB.setText(question[1].getAnswers().get(2));
            answerDplayerB.setText(question[1].getAnswers().get(3));
        }//*/

    }


    private void answersReady() {
        boolean send = false;
        for(int i = 0; i<players; i++) {
            if(answers[i] > 0 && answers[i] < 5) {
                send = true;
            } else send = false;
        }
        if(send) {
            dbg.info("" + answers[0] + " // " + answers[1]);
            game.ready(answers);
            answers[0] = -1;
            answers[1] = -1;
            game.newQuestion();
        }
    }
    public void next() {
        label.setText("New round");
        label.setForeground(Color.CYAN);

        JButton nxt = new JButton("Continue.");
        nxt.setPreferredSize(new Dimension(100, 20));
        nxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                asd();
            }
        });
        botPanel.add(nxt, BorderLayout.LINE_END);
    }

    private void asd() {
        this.game.newQuestion();
    }
    private void startG() {
        System.out.println(players);
        this.game.setPlayers(players);
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
