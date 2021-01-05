import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
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

    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JLabel lTopEast;
    private JLabel lTopWest;
    private JLabel lMidEast;
    private JLabel lMidWest;
    private JLabel lBotEast;
    private JLabel lBotWest;

    private JButton like;
    private JButton dislike;
    private int players;

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

    }


    public int choosePlayers() {
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
        return players;
    }

    private void updatePlayers() {
        if(players==1) label2.setText("You chose one player.");
        else label2.setText("You chose two players.");
        label2.setForeground(Color.RED);

        JButton bContinue = new JButton("Continue.");
        bContinue.setPreferredSize(new Dimension(100, 20));
        bContinue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                label.setText("Buzz! Quiz world is about to begin with: " + players + " player(s).");
                label.setForeground(Color.CYAN);
                try {
                    Thread.sleep(1000);
                    wipeAll();
                    init();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        botPanel.add(bContinue, BorderLayout.LINE_END);
    }

    private void wipeAll() {
        topPanel.removeAll();
        midPanel.removeAll();
        botPanel.removeAll();
        frame.repaint();
    }

    public void init() {
        pTopEast = new JPanel();
        pTopEast.setLayout(new FlowLayout(FlowLayout.LEADING));
        pTopEast.setBackground(Color.GREEN);
        pTopEast.setPreferredSize(new Dimension(topPanel.getSize().width/2, topPanel.getSize().height));
        topPanel.add(pTopEast);

        pTopWest = new JPanel();
        pTopWest.setLayout(new FlowLayout(FlowLayout.LEADING));
        pTopWest.setBackground(Color.RED);
        pTopWest.setPreferredSize(new Dimension(topPanel.getSize().width/2, topPanel.getSize().height));
        topPanel.add(pTopWest);
        lTopEast = new JLabel("Player 1 panel");
        pTopEast.add(lTopEast);
        lTopWest = new JLabel("Player 2 panel");
        pTopWest.add(lTopWest);

        pMidEast = new JPanel();
        pMidEast.setLayout(new FlowLayout(FlowLayout.LEADING));
        pMidEast.setBackground(Color.GREEN);
        pMidEast.setPreferredSize(new Dimension(midPanel.getSize().width/2, midPanel.getSize().height));
        midPanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,10));
        midPanel.add(pMidEast);

        pMidWest = new JPanel();
        pMidWest.setLayout(new FlowLayout(FlowLayout.LEADING));
        pMidWest.setBackground(Color.RED);
        pMidWest.setPreferredSize(new Dimension(midPanel.getSize().width/2, midPanel.getSize().height));
        midPanel.add(pMidWest);
        lMidEast = new JLabel("Player 1 panel");
        pMidEast.add(lMidEast);
        lMidWest = new JLabel("Player 2 panel");
        pMidWest.add(lMidWest);

        pBotEast = new JPanel();
        pBotEast.setLayout(new FlowLayout(FlowLayout.LEADING));
        pBotEast.setBackground(Color.GREEN);
        pBotEast.setPreferredSize(new Dimension(botPanel.getSize().width/2, botPanel.getSize().height));
        botPanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,10));
        botPanel.add(pBotEast);

        pBotWest = new JPanel();
        pBotWest.setLayout(new FlowLayout(FlowLayout.LEADING));
        pBotWest.setBackground(Color.RED);
        pBotWest.setPreferredSize(new Dimension(botPanel.getSize().width/2, botPanel.getSize().height));
        botPanel.add(pBotWest);
        lBotEast = new JLabel("Player 1 panel");
        pBotEast.add(lBotEast);
        lBotWest = new JLabel("Player 2 panel");
        pBotWest.add(lBotWest);

        frame.repaint();

    }

    public void game() {

    }
    /**
     * setVisible when you are ready
     */
    public void start() {
        frame.setVisible(true);
    }

}
