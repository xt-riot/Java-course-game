import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends Panel {
    private final Label panelLabel;
    private final Timer timer;

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

    public void startRendering() {
        super.startRenderingImage(150, true);
        this.rendering = true;
    }

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