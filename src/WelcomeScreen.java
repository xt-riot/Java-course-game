import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends Panel {
    private final Label panelLabel;
    private final GUILogic parent;
    private PlayerChoice player;
    private Timer timer;
    private int panels;

    public WelcomeScreen(JFrame id, GUILogic callerID) {
        super(id, true);
        this.hasbuttons = false;
        this.timer = new Timer(1, x->{});
        //System.out.println(this.isPrimary);
        //this.labelMidOfScreen = new CustomLabel(this, "Welcome to Buzz! Quiz world rip-off");
        //this.alpha = 0;
        //this.readyToLeave = false;
        parent = callerID;
        //this.player = new PlayerChoice(this.frame, parent);
        //this.getImg();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = Main.WIDTH /2;
        constraints.gridy = 1;
        //this.labelMidOfScreen.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        //this.labelMidOfScreen.setForeground(new Color(255,255,255, 0));
        //this.labelMidOfScreen.setVisible(false);
        //this.add(this.labelMidOfScreen, constraints);
        panelLabel = new Label(this.frame, "Welcome to Buzz! Quiz world rip-off");
        this.add(this.panelLabel);
        this.panels = 0;
        //System.out.println(this.isPrimary);

    }

    public void startRendering() {
        super.startRenderingImage(150, true);
        this.rendering = true;
        //super.fadeIn();

        //super.fadeIn(90);
        //super.startRenderingImage(150);
        //if(!this.isPrimary) super.fadeIn(90);
        //else super.startRenderingImage(150);
    }

    public void unRender(int delay) {
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            if(this.panelLabel.isShown() && this.isShown && this.panelLabel.isRendering()) {
                this.panelLabel.unRender(0);
            }
            else if (!this.panelLabel.isShown() && this.panelLabel.isRendering()) {
                //System.out.println("HELLO");
                super.startRenderingImage(Main.WIDTH);
                this.panels = 0;
                System.out.println(this.getClass().getName() + " has been ordered to fade out.");
                this.timer.stop();
            }
        });
        this.timer.setInitialDelay(delay);
        this.timer.start();
    }



    /*@Override
    protected void nextRender() {
        ArrayList<CustomLabel> temp = new ArrayList<>();
        for(Component e : this.getComponents()) {
            if ( e instanceof CustomLabel) {
                temp.add((CustomLabel) e);
            }
        }
        ActionListener action = e -> {};
        Timer asd = new Timer(50, action);
        if(!this.readyToLeave) {
            action = e -> {
                if (temp.size() > 0) {
                    if (!temp.get(0).rendering && !temp.get(0).rendered) temp.get(0).fadeIn(90);
                    if (temp.get(0).rendered) temp.remove(0);
                } else {
                    ((Timer) e.getSource()).stop();
                    unRender();
                }
            };
            asd.removeActionListener(asd.getActionListeners()[0]);
            asd.addActionListener(action);
            asd.start();
        }
        else {
            action = e -> {
                if(temp.size() > 0) {
                    if(!temp.get(0).rendering && temp.get(0).rendered) temp.get(0).fadeOut();
                    if(!temp.get(0).rendered) temp.remove(0);
                }
                else {
                    readyToLeave = false;

                    Timer tmp = (Timer) e.getSource();
                    tmp.addActionListener(x->{ if(safeRemove) goNext(); });
                    tmp.removeActionListener(tmp.getActionListeners()[0]);
                }};
            asd.removeActionListener(asd.getActionListeners()[0]);
            asd.addActionListener(action);
            asd.start();
        }
    }
    @Override
    protected void goNext() {
        this.readyToLeave = false;
        this.imgCurrentPosition = -(Main.WIDTH);
        this.parent.nextStep(2);
        //this.player.startRendering();
    }//*/
}