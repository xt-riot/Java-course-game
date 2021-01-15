import javax.swing.*;
import java.awt.*;

/**
 *Κλάση WelcomeScreen η οποία είναι επέκταση της κλάσης Panel
 * και χρησιμοποιείται στην έναρξη του παιχνιδιού.
 */
public class WelcomeScreen extends Panel {
    private final Label panelLabel;
    private final Timer timer;

    /**
     * Κατασκευαστής της κλάσης WelcomeScreen
     * @param id
     */
    public WelcomeScreen(JFrame id) {
        super(id, true);
        this.hasbuttons = false;
        this.timer = new Timer(1, x->{});
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = Main.WIDTH /2;
        constraints.gridy = 1;
        panelLabel = new Label(this.frame, "Welcome to Buzz! Quiz world rip-off");
        this.add(this.panelLabel);
        this.panels = 0;
    }

    /**
     *Μέθοδος για την κίνηση της εικόνας.
     */
    public void startRendering() {
        super.startRenderingImage(150, true);
        this.rendering = true;
    }

    /**
     *Μέθοδος που διατηρεί το αρχικό πλάσιο για μερικά δευτερόλεπτα.
     * @param delay
     */
    public void unRender(int delay) {
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            if(this.panelLabel.isShown() && this.isShown && this.panelLabel.isRendering()) {
                this.panelLabel.unRender(0);
            }
            else if (!this.panelLabel.isShown() && this.panelLabel.isRendering()) {
                super.startRenderingImage(Main.WIDTH);
                this.panels = 0;
                this.timer.stop();
            }
        });
        this.timer.setInitialDelay(delay);
        this.timer.start();
    }
}