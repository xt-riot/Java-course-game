import javax.swing.*;
import java.awt.*;

class Label extends Panel {
    private CustomLabel label;
    private int panelComponents;
    private boolean counted;

    private Timer timer;

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
        //this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    public void amIReady() {
        if (!this.label.isVisible()) this.remove(this.label);
    }

    public void setText(String txt) {
        this.label.setText(txt);
    }


    public void startRendering() {
        //super.fadeIn(this);
        this.rendering = true;
        this.timer.removeActionListener(this.timer.getActionListeners()[0]);
        this.timer.addActionListener(x -> {
            //System.out.println(this.label.isRendering());
            if(this.frame.isVisible() && this.label.isShown() && this.label.isCounted()) {
                this.panelComponents++;
                this.label.setCounted(true);
            }
            else if(this.frame.isVisible() && this.label.isShown() && this.getComponentCount() == this.panelComponents) {
                this.panelComponents = 0;
                this.isShown = true;
                this.rendering = false;
                System.out.println(this.getClass().getName() + " has completed fading in.");
                this.timer.stop();
            }
            else if (this.frame.isVisible() && this.label.isRendering()) {
                //System.out.println(this.getRootPane());
                System.out.println(this.getClass().getName() + " has requested " + this.label.getClass().getName() + " to fade in.");
                //this.rendering = true;
                this.label.fadeIn(90);
            }
        });
        this.timer.start();
        //this.isShown = true;
        /*for(Component e : this.getComponents()) {
            if ( e instanceof CustomLabel) {
                ((CustomLabel) e).fadeIn(10);
            }
        }//*/
    }

    public void unRender(int delay) {
        //super.fadeOut(90);

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
                System.out.println(this.getClass().getName() + " has completed fading out.");
                this.timer.stop();
            }
            else if (this.label.isRendering()) {
                System.out.println(this.getClass().getName() + " has requested " + this.label.getClass().getName() + " to fade out.");
                this.label.fadeOut();
            }
        });
        this.timer.start();

        /*for(Component e : this.getComponents()) {
            if ( e instanceof CustomLabel) {
                ((CustomLabel)e).fadeOut();
            }
        }//*/
    }

    public boolean isShown() {
        return this.isShown;
    }

    public boolean isRendering() {
        return !this.rendering;
    }

    public void setCounted( boolean bool) {
        this.counted = bool;
    }
    public boolean isCounted() {
        return !this.counted;
    }
}