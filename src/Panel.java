import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 *Κλάση abstract Panel που κληρονομεί απο την JPanel
 * και λειτουργεί ως πάνελ για την εφαρμογή.
 */
abstract class Panel extends JPanel {
    protected JFrame frame;

    protected boolean isPrimary;
    protected boolean isShown;
    protected boolean rendering;
    protected boolean hasbuttons;
    protected boolean counted;
    protected int imgCurrentPosition;

    private BufferedImage img;
    private final Timer timerForAnimations;
    protected int panels;
    protected boolean hasAnswers;
    protected boolean renderAll;

    protected int panelID;


    /**
     * Κατασκευαστής της κλάσης abstract Panel.
     * @param id
     * @param isPrim
     */
    Panel(JFrame id, boolean isPrim) {
        this.frame = id;
        this.isPrimary = isPrim;
        this.setLayout(new GridBagLayout());
        this.imgCurrentPosition = -(Main.WIDTH);
        this.renderAll = false;
        if(this.isPrimary) {
            this.img = new CustomImage().getImage();
        }
        this.timerForAnimations = new Timer(50, e -> {});
        this.setBackground(Color.BLACK);
        panelID = 0;
    }

    abstract public void startRendering();
    abstract public void unRender(int delay);

    protected void fadeIn(Component x) {
        if (x instanceof Label) {
            if (!((Label) x).isShown() && ((Label) x).isRendering()) {
                ((Label) x).startRendering();
            } else if (((Label) x).isShown() && ((Label) x).isCounted()) {
                ((Label) x).setCounted(true);
                this.panels++;
            }
        } else if (x instanceof Buttons) {
            if (!((Buttons) x).isShown() && ((Buttons) x).isRendering()) {
                ((Buttons) x).setCoordinates(x.getX(), x.getY(), Main.HEIGHT);
                ((Buttons) x).render();
                ((Buttons) x).startRendering();
            } else if (((Buttons) x).isShown() && ((Buttons) x).isCounted()) {
                ((Buttons) x).setCounted(true);
                this.panels++;
            }
        } else  {
            System.out.println(this.getClass().getName() + " is neither a label or a button.");
        }

    }
    
    protected void fadeIn() {
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
                this.timerForAnimations.stop();
            } else if (this.panels < this.getComponentCount() && !this.rendering) {
                Component x = this.getComponent(this.panels);
                this.fadeIn(x);
            }
        });
        this.timerForAnimations.start();
    }

    protected void fadeOut(Panel panelToFadeOut) {
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(e -> {
            for(Component panels : this.getComponents()) {
                if(panelToFadeOut == this.getComponent(panelToFadeOut.getPanelID())) {
                    panelToFadeOut.unRender(0);

                    this.timerForAnimations.stop();
                }
            }
        });
        this.timerForAnimations.start();
    }


    /**
     * Μέθοδος για την κίνηση της εικόνας.
     * @param imageFinalPosition τελική εικόνα εφαρμογής.
     */
    protected void startRenderingImage(int imageFinalPosition) {
        this.rendering = true;
        ActionListener action = e -> {
            repaint();
            if(imgCurrentPosition >= imageFinalPosition && imageFinalPosition < Main.WIDTH) {
                imgCurrentPosition = imageFinalPosition;
                isShown = true;
                rendering = false;
                this.timerForAnimations.stop();
                if(this.renderAll) this.fadeIn();
            }
            else if (imgCurrentPosition > imageFinalPosition && imageFinalPosition == Main.WIDTH) {
                isShown = false;
                rendering = false;
                this.timerForAnimations.stop();
            }
            else {
                rendering = true;
                imgCurrentPosition += Main.WIDTH/(1000/Main.speed);
            }
        };
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(action);
        this.timerForAnimations.setDelay(10);
        this.timerForAnimations.start();
    }

    /**
     * Μέθοδος για την κίνηση της εικόνας.
     * @param imageFinalPosition τελική εικόνα εφαρμογής.
     * @param bool
     */
    protected void startRenderingImage(int imageFinalPosition, boolean bool) {
        this.renderAll = bool;
        this.startRenderingImage(imageFinalPosition);
    }

    public boolean isShown() {
        return this.isShown;
    }

    public boolean isRendering() {
        return !this.rendering;
    }

    public boolean hasButtons() {
        return !this.hasbuttons;
    }

    protected void setPanelID(int id) {
        this.panelID = id;
    }

    public int getPanelID() {
        return this.panelID;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, imgCurrentPosition, 0, null);
    }
    @Override
    public Component add(Component comp) {
        super.add(comp);
        ((Panel) comp).setPanelID(this.panelID++);
        return comp;
    }
}