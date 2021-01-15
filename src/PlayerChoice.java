import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Κλάση PlayerChoice που είναι επέκταση της κλάσης Panel
 * και η οποία βοηθάει για την δημιουργία κουμπίων λόγο του πλέγματος (grid).
 */
public class PlayerChoice extends Panel {

    private final GUILogic parent;
    private final Buttons buttons;
    private final Label label;

    private int numberOfPlayers;
    private final Timer timer;
    private int panels;

    /**
     * Κατασκευαστής της κλάσης PlayerChoice
     * @param id
     * @param testid
     */
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

        this.buttons.addPropertyChangeListener("numberOfPlayers", evt -> setNumberOfPlayers((int)evt.getNewValue()));
    }

    /**
     * Μέθοδος για την κίνηση της εικόνας.
     */
    @Override
    public void startRendering() {
        super.startRenderingImage(150, true);
        this.rendering = true;

    }

    /**
     * Μέθοδος που διατηρεί το αρχικό πλάσιο για μερικά δευτερόλεπτα
     * και εμφανίζει κάποια κουμπιά.
     * @param delay χρόνος καθυστερησης
     */
    @Override
    public void unRender(int delay) {
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && this.isShown) {
                super.startRenderingImage(Main.WIDTH);
                this.parent.choosePlayerName();
                this.timer.stop();
            }
            else if(this.panels < this.getComponentCount() && this.isShown) {
                Component x = this.getComponent(this.getComponentCount() - 1 - this.panels);
                if( x instanceof Label ) {
                    if( ((Label) x).isShown() && ((Label) x).isRendering()) {
                        ((Label) x).unRender(0);
                    }
                    else if ( !((Label) x).isShown() && ((Label) x).isCounted()) {
                        ((Label) x).setCounted(true);
                        this.panels++;
                    }
                }
                else if ( x instanceof Buttons ) {
                    if ( ((Buttons) x).isShown() && ((Buttons) x).isRendering()) {
                        ((Buttons) x).unRender(0);
                    }
                    else if ( !((Buttons) x).isShown() && ((Buttons) x).isCounted()) {
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

    /**
     * Μέθοδος που επιλέγει τον αριθμό των παικτών (1 ή 2).
     * @param players Παίκτης του παιχνιδιού.
     */
    public void setNumberOfPlayers(int players) {
        this.numberOfPlayers = players;
        this.parent.playersChosen(this.numberOfPlayers);

    }
}
