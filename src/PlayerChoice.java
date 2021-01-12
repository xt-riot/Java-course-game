import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerChoice extends Panel {

    private final GUILogic parent;
    private Buttons buttons;
    private Label label;

    private int numberOfPlayers;
    private Timer timer;
    private int panels;

    public PlayerChoice(JFrame id, GUILogic testid) {
        super(id, true);
        this.parent = testid;
        this.setVisible(true);
        this.hasbuttons = true;
        this.timer = new Timer(50, x-> {});
        label = new Label(id, "Choose how many players to play with.");
        buttons = new Buttons(id, Main.maximumPlayers);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        this.add(label, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;

        this.add(buttons, gbc);

        this.buttons.addPropertyChangeListener("numberOfPlayers", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Object source = evt.getSource();
                System.out.println(this.getClass().getName() + " //" + source.toString() + " asdasdadsad " + evt.getNewValue());
                setNumberOfPlayers((int)evt.getNewValue());
            }
        });
    }

    @Override
    public void startRendering() {
        super.startRenderingImage(150, true);
        this.rendering = true;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
                System.out.println(this.getClass().getName() + " has completed fading in with all its components.");
                this.timer.stop();
            }
            else if(this.panels < this.getComponentCount() && !this.rendering) {
                Component x = this.getComponent(this.panels);
                if (x instanceof Label) {
                    if (!((Label) x).isShown() && ((Label) x).isRendering()) {
                        System.out.println(x.getClass().getName() + " has been ordered to render.");
                        ((Label) x).startRendering();
                    } else if (((Label) x).isShown() && ((Label) x).isCounted()) {
                        System.out.println(((Label) x).getClass().getName() + " has completed rendering.");
                        ((Label) x).setCounted(true);
                        this.panels++;
                    }
                } else if (x instanceof Buttons) {
                    if (!((Buttons) x).isShown() && ((Buttons) x).isRendering()) {
                        ((Buttons) x).setCoordinates(x.getX(), x.getY(), Main.HEIGHT);
                        ((Buttons) x).startRendering();
                    } else if (((Buttons) x).isShown() && ((Buttons) x).isCounted()) {
                        System.out.println(((Buttons) x).getClass().getName() + " has completed rendering.");
                        ((Buttons) x).setCounted(true);
                        this.panels++;
                    }
                } else {
                    System.out.println(this.getClass().getName() + " is neither a label or a button.");
                }

            }
        });
        this.timer.start();
    }

    @Override
    public void unRender(int delay) {
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && this.isShown) {
                super.startRenderingImage(Main.WIDTH);
                this.parent.choosePlayerName();
                System.out.println(this.getClass().getName() + " has completed fading out all its components. Starting fading out the background image.");
                this.timer.stop();
            }
            else if(this.panels < this.getComponentCount() && this.isShown) {
                Component x = this.getComponent(this.getComponentCount() - 1 - this.panels);
                if( x instanceof Label ) {
                    if( ((Label) x).isShown() && ((Label) x).isRendering()) {
                        System.out.println(x.getClass().getName() + " has been ordered to unrender.");
                        ((Label) x).unRender(0);
                    }
                    else if ( !((Label) x).isShown() && ((Label) x).isCounted()) {
                        System.out.println(((Label) x).getClass().getName() + " has completed unrendering.");
                        ((Label) x).setCounted(true);
                        this.panels++;
                    }
                }
                else if ( x instanceof Buttons ) {
                    if ( ((Buttons) x).isShown() && ((Buttons) x).isRendering()) {
                        System.out.println(x.getClass().getName() + " has been ordered to unrender.");
                        ((Buttons) x).setCoordinates(x.getX(), Main.HEIGHT, x.getY());
                        ((Buttons) x).unRender(0);
                    }
                    else if ( !((Buttons) x).isShown() && ((Buttons) x).isCounted()) {
                        System.out.println(((Buttons) x).getClass().getName() + " has completed unrendering.");
                        ((Buttons) x).setCounted(true);
                        this.panels++;
                    }
                }
                else {
                    System.out.println(this.getClass().getName() + " is neither a label or a button.");
                }
            }
        });
        this.timer.start();
    }



    /*

    for( Component x : this.getComponents()) {
                    if( x instanceof Label ) {
                        if( ((Label) x).isShown() && !((Label) x).isRendering() ) {
                            System.out.println(x.getClass().getName() + " has been ordered to unrender.");
                            ((Label) x).unRender();
                        }
                        else if ( !((Label) x).isShown() && !((Label) x).isCounted()) {
                            System.out.println(((Label) x).getClass().getName() + " has completed unrendering.");
                            ((Label) x).setCounted(true);
                            this.panels++;
                        }
                    }
                    else if ( x instanceof Buttons ) {
                        if ( ((Buttons) x).isShown() && !((Buttons) x).isRendering() ) {
                            System.out.println(x.getClass().getName() + " has been ordered to unrender.");
                            ((Buttons) x).unRender();
                        }
                        else if ( !((Buttons) x).isShown() && !((Buttons) x).isCounted() ) {
                            System.out.println(((Buttons) x).getClass().getName() + " has completed unrendering.");
                            ((Buttons) x).setCounted(true);
                            this.panels++;
                        }
                    }
                    else {
                        System.out.println(this.getClass().getName() + " is neither a label or a button.");
                    }
                }
     if (this.panelLabel.isShown()) {
                    this.panels = 0;
                    this.isShown = true;
                    this.rendering = false;
                    System.out.println(this.getClass().getName() + " has completed fading in.");
                    this.timer.stop();
                }
                else if (!this.panelLabel.isRendering()) {
                    this.panelLabel.startRendering();
                }


    @Override

    protected void nextRender() {
            for(Component e : (this.getComponents())) {
                if(e instanceof Panel ) {
                    if(!this.readyToLeave) ((Panel)e).startRendering();
                    else if(e instanceof TopLabel ) ((Panel)e).unRender();
                }
            }

    }

    @Override
    protected void goNext() {
        //parent.nextStep(3);
        //this.unRender();
        this.parent.playersChosen(this.numberOfPlayers);
    }//*/

    public void setNumberOfPlayers(int players) {
        this.numberOfPlayers = players;
        this.parent.playersChosen(this.numberOfPlayers);

    }
}
