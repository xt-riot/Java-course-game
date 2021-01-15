import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class Buttons extends Panel {
    private CustomButton[] players;
    private int endX;
    private int endY;
    private int currentY;

    private final Timer timer;

    Buttons(JFrame id) {
        super(id, false);
        this.setBackground(new Color(0,0,0,0));
        this.timer = new Timer(1, e->{});
        this.counted = false;
    }

    Buttons(JFrame id, int numberOfButtons) {
        this(id);
        this.players = new CustomButton[numberOfButtons];
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0,0,0, Main.WIDTH / (numberOfButtons * 8) );
        for (int i = 0; i<numberOfButtons; i++) {
            this.players[i] = new CustomButton(this.frame ,(i+1) + " player(s).");
            int k = i+1;
            this.players[i].addActionListener(e -> this.firePropertyChange("numberOfPlayers", 0, k));
            constraints.gridx = i;
            constraints.gridy = 2;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.ipady = 50;
            constraints.ipadx = 50;
            if(i == numberOfButtons-1) constraints.insets = new Insets(0,Main.WIDTH / (numberOfButtons * 8), 0, 0 );
            CustomButton e = this.players[i];
            this.add(e, constraints);
        }

    }

    Buttons(JFrame id, int numberOfButtons, boolean vertical) {
        this(id);
        this.players = new CustomButton[numberOfButtons];
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0,0,0, Main.WIDTH / (numberOfButtons * 8) );
        for (int i = 0; i<numberOfButtons; i++) {
            this.players[i] = new CustomButton(this.frame ,(i+1) + " player(s).");
            int k = i;
            constraints.gridx = i;
            constraints.gridy = 0;
            constraints.fill = GridBagConstraints.BOTH;
            constraints.ipady = 50;
            constraints.ipadx = 50;
            this.players[i].addActionListener(e -> this.firePropertyChange("playerHasAnswered", 1, this.players[k].getText()));
            if(i == numberOfButtons-1) constraints.insets = new Insets(0,Main.WIDTH / (numberOfButtons * 8), 0, 0 );
            if(vertical) {
                int temp = constraints.gridx;
                constraints.gridx = constraints.gridy;
                constraints.gridy = temp;
                constraints.insets = new Insets(15,0 ,0 ,0 );
                constraints.ipady = 50/numberOfButtons;
            }
            CustomButton e = this.players[i];
            this.add(e, constraints);
        }
    }

    public void setText(ArrayList<String> text) {
        Collections.shuffle(text);
        for(int i = 0; i < this.players.length; i++) {
            this.players[i].set(text.get(i));
            this.players[i].render(true);
        }
    }

    public void setCoordinates(int x, int y, int wantedY) {
        this.endX = x;
        this.endY = y;
        this.currentY = wantedY;
    }

    public void render() {
        for( CustomButton x : this.players)
            x.render(true);
    }

    public String getAnswer(int button) {
        return this.players[button].getText();
    }


    @Override
    public void startRendering() {
        this.rendering = true;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            this.currentY -= Main.HEIGHT/20;
            if (this.currentY <= this.endY) {
                this.timer.stop();
                this.currentY = this.endY;
                this.isShown = true;
                this.rendering = false;
            } else if (this.currentY >= Main.HEIGHT - this.endY) {
                for( CustomButton e : this.players) {
                    e.render(true);
                }
            }
            this.setLocation(endX, currentY);

        });
        this.timer.start();
    }

    @Override
    public void unRender(int delay) {
        this.rendering = true;
        this.counted = false;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            this.currentY += Main.HEIGHT/50;
            if (this.currentY >= Main.HEIGHT) {
                this.timer.stop();
                this.currentY = Main.HEIGHT;
                this.isShown = false;
                this.rendering = false;
                if(delay == 0) {
                    for (CustomButton e : this.players)
                        e.render(false);
                }
            }
            this.setLocation(endX, currentY);
        });
        this.timer.setInitialDelay(0);
        this.timer.start();
    }

    public void setCounted( boolean bool) {
        this.counted = bool;
    }
    public boolean isCounted() {
        return !this.counted;
    }
}