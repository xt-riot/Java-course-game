import javax.swing.*;
import java.awt.*;

class Label extends Panel {
    private final CustomLabel label;
    private int panelComponents;

    private final Timer timer;

    Label (JFrame id, String txt) {
        super(id, false);
        this.timer = new Timer(50, x->{});
        this.panelComponents = 0;
        this.counted = false;
        this.setBackground(new Color(0,0,0,0));
        this.label = new CustomLabel(this, txt);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipadx = Main.WIDTH /2;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 50,0 );
        this.label.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
        this.label.setForeground(new Color(255,255,255, 0));
        this.label.setVisible(false);
        this.add(this.label, constraints);
    }

    public void setText(String txt) {
        this.label.setText(txt);
    }


    public void startRendering() {
        this.rendering = true;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            if(this.frame.isVisible() && this.label.isShown() && this.label.isCounted()) {
                this.panelComponents++;
                this.label.setCounted(true);
            }
            else if(this.frame.isVisible() && this.label.isShown() && this.getComponentCount() == this.panelComponents) {
                this.panelComponents = 0;
                this.isShown = true;
                this.rendering = false;
                this.timer.stop();
            }
            else if (this.frame.isVisible() && this.label.isRendering()) {
                this.label.fadeIn(90);
            }
        });
        this.timer.start();
    }

    public void unRender(int delay) {
        this.rendering = true;
        this.counted = false;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            if(!this.label.isShown() && this.label.isCounted()) {
                this.panelComponents++;
                this.label.setCounted(true);
            }
            else if(!this.label.isShown() && this.label.isRendering() && this.isShown) {
                this.panelComponents = 0;
                this.isShown = false;
                this.rendering = false;
                this.timer.stop();
            }
            else if (this.label.isRendering()) {
                this.label.fadeOut();
            }
        });
        this.timer.start();

    }

    public void setCounted( boolean bool) {
        this.counted = bool;
    }
    public boolean isCounted() {
        return !this.counted;
    }
}