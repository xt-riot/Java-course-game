import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomButton extends JButton{
    protected JFrame panel;
    private String txt;

    private boolean counted;
    private boolean rendered;
    private boolean rendering;

    public CustomButton(JFrame panelID, String text) {
        super();
        this.panel = panelID;
        this.rendering = false;
        this.rendered = false;
        this.counted = false;

        /*this.playerTimer = new Timer(1, e -> {
            currentY -= Main.HEIGHT/20;
            slideButton();
        });//*/
        this.txt = text;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setEnabled(false);
        this.setForeground(new Color(0,0,0,255));
    }

    public void fadeIn() {
        /*this.setLocation(this.endX, this.currentY);
        this.getRootPane().invalidate();
        this.getRootPane().validate();
        this.getRootPane().repaint();
        this.setForeground(new Color(0,0,0));
        this.setText(txt);
        this.setVisible(true);
        this.setOpaque(true);
        this.setContentAreaFilled(true);
        this.setBorderPainted(true);
        this.playerTimer.start();
        setEnabled(false);
        this.rendering = true;//*/

    }

    public void render(boolean bool) {
        this.setVisible(bool);
        this.setOpaque(bool);
        this.setContentAreaFilled(bool);
        this.setBorderPainted(bool);
        this.setEnabled(bool);
        this.setText(this.txt);
    }

    public void set(String txt) {
        this.txt = txt;
    }

    public void fadeOut() {
        /*action = e -> {
            currentY += Main.HEIGHT/15;
            slideButton();
        };
        this.playerTimer.removeActionListener(this.playerTimer.getActionListeners()[0]);
        this.playerTimer.addActionListener(action);
        this.getRootPane().invalidate();
        this.getRootPane().validate();
        this.getRootPane().repaint();
        System.out.println(this.getClass().getName() + " has been ordered to fade out.");
        this.playerTimer.start();
        this.rendering = true;
        this.counted = false;//*/

    }

    /*protected void slideButton() {

        if ( !rendered && this.currentY <= this.endY ) {
            this.currentY = this.endY;
            this.rendered = true;
            this.rendering = false;
            this.setEnabled(true);
            this.getRootPane().invalidate();
            this.getRootPane().validate();
            this.getRootPane().repaint();
            //System.out.println("y: " + this.getY() + " // " + this.getX());
            this.playerTimer.stop();
        }
        else if (rendered && this.currentY >= this.endY) {
            this.rendered = false;
            this.rendering = false;
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
            //this.setEnabled(false);
            this.setForeground(null);
            this.setBackground(null);
            this.setText(null);
            //System.out.println("y: " + this.getY() + " // " + this.getX());
            this.getRootPane().invalidate();
            this.getRootPane().validate();
            this.getRootPane().repaint();
            this.repaint();
            this.playerTimer.stop();
        }

        this.setLocation(endX, currentY);
    }//*/


    public boolean isShown() {
        return this.rendered;
    }

    public boolean isRendering() {
        return this.rendering;
    }

    public boolean isCounted() {
        return this.counted;
    }

    public void setCounted(boolean bool) {
        this.counted = bool;
    }
}
