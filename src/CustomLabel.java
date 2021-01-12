import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomLabel extends JLabel {
    public boolean rendered;
    public boolean rendering;
    protected JPanel panel;
    private int currentAlpha;
    private final Timer asd;
    private int fade;

    private boolean counted;

    public CustomLabel(JPanel panelID, String text) {
        super(text);
        this.panel = panelID;
        this.counted = false;
        this.asd = new Timer(1, e -> {
            currentAlpha += 255 / fade;
            fadeLabel();
        });
        this.rendered = false;
        this.rendering = false;
    }
    public void fadeIn(int f) {
        this.currentAlpha = 0;
        this.setVisible(true);
        this.fade = f;
        this.asd.start();
        this.rendering = true;
        this.counted = false;
    }

    public void fadeOut() {
        this.currentAlpha = 255;
        ActionListener action = e -> {
            currentAlpha -= 255 / 90;
            fadeLabel();
        };
        this.rendering = true;
        this.counted = false;
        this.asd.removeActionListener(this.asd.getActionListeners()[0]);
        this.asd.addActionListener(action);
        this.asd.start();

    }

    protected void fadeLabel() {
        if (currentAlpha > 255) {
            currentAlpha = 255;
            this.asd.stop();
            this.rendered = true;
            this.rendering = false;
        } else if (currentAlpha < 0) {
            currentAlpha = 0;
            this.setVisible(false);
            this.rendered = false;
            this.rendering = false;
            this.asd.stop();
        }
        this.getRootPane().invalidate();
        this.getRootPane().validate();
        this.getRootPane().repaint();
        this.setForeground(new Color(255, 255, 255, this.currentAlpha));//*/
    }

    public boolean isShown() {
        return this.rendered;
    }

    public boolean isRendering() {
        return !this.rendering;
    }

    public boolean isCounted() {
        return !this.counted;
    }

    public void setCounted(boolean bool) {
        this.counted = bool;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        if(this.getRootPane() != null ) {
            this.getRootPane().invalidate();
            this.getRootPane().validate();
            this.getRootPane().repaint();

        }

    }


}
