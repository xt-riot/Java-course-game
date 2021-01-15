import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;




abstract class Panel extends JPanel {
    protected static final Logger dbg = Logger.getLogger(Panel.class.getName());
    protected JFrame frame;

    protected boolean isPrimary;
    protected boolean isShown;
    protected boolean rendering;
    protected boolean hasbuttons;
    protected boolean counted;
    protected int imgCurrentPosition;

    private BufferedImage img;
    private Timer timerForAnimations;
    private int componentsReady;
    protected int panels;
    protected boolean hasAnswers;
    protected boolean renderAll;

    protected int panelID;


    Panel(JFrame id, boolean isPrim) {
        this.frame = id;
        this.isPrimary = isPrim;
        this.componentsReady = 0;
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
                //this.firePropertyChange("start", 0, 1);
                System.out.println(x.getClass().getName() + " has been ordered to render.");
                ((Label) x).startRendering();
            } else if (((Label) x).isShown() && ((Label) x).isCounted()) {
                System.out.println(((Label) x).getClass().getName() + " has completed rendering.");
                ((Label) x).setCounted(true);
                this.panels++;
            }
        } else if (x instanceof Buttons) {
            if (!((Buttons) x).isShown() && ((Buttons) x).isRendering()) {
                System.out.println(this.getClass().getName() + " has requested " + x.getClass().getName() + " to start rendering.");
                ((Buttons) x).setCoordinates(x.getX(), x.getY(), Main.HEIGHT);
                ((Buttons) x).render();
                ((Buttons) x).startRendering();
            } else if (((Buttons) x).isShown() && ((Buttons) x).isCounted()) {
                System.out.println(((Buttons) x).getClass().getName() + " has completed rendering.");
                ((Buttons) x).setCounted(true);
                this.panels++;
            }
        } else  {
            System.out.println(this.getClass().getName() + " is neither a label or a button.");
        }

    }
    
    protected void fadeIn() {
        //System.out.println(this.timerForAnimations.isRunning());
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && !this.rendering) {
                this.isShown = true;
                this.panels = 0;
                System.out.println(this.getClass().getName() + " has completed fading in with all its components.");
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
                    System.out.println(panelToFadeOut.getClass().getName() + " id: " + panelToFadeOut.getPanelID() + "(Panel.fadeOut)");
                    panelToFadeOut.unRender(0);

                    this.timerForAnimations.stop();
                }
            }
        });
        this.timerForAnimations.start();
    }


    protected void startRenderingImage(int imageFinalPosition) {
        this.rendering = true;
        ActionListener action = e -> {
            //this.invalidate();
            //this.validate();
            repaint();
            if(imgCurrentPosition >= imageFinalPosition && imageFinalPosition < Main.WIDTH) {
                imgCurrentPosition = imageFinalPosition;
                isShown = true;
                rendering = false;
                //
                //System.out.println(this.getClass().getName() + " says hi." + this.renderAll);
                this.timerForAnimations.stop();
                if(this.renderAll) this.fadeIn();
            }
            else if (imgCurrentPosition > imageFinalPosition && imageFinalPosition == Main.WIDTH) {
                isShown = false;
                rendering = false;
                System.out.println(this.getClass().getName() + " has completed fading out.(Panel)");

                this.timerForAnimations.stop();
            }
            else {
                rendering = true;
                //System.out.println(this.getClass().getName() + " changing current position.");
                imgCurrentPosition += Main.WIDTH/(1000/Main.speed);
            }
            //System.out.println(this.getClass().getName() + " requested to bring background image to position: " + imageFinalPosition + ". Image shown (" + isShown + ") Image rendering (" + rendering + ") Image current position: " + imgCurrentPosition);
        };
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(action);
        this.timerForAnimations.setDelay(10);
        this.timerForAnimations.start();
        //System.out.println(this.timerForAnimations.getClass().getName() + " timerForAnimations is " + this.timerForAnimations.isRunning() + " with " + this.timerForAnimations.getActionListeners().length + " action listeners.");
    }

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
    public int getImagePosition() {
        return this.imgCurrentPosition;
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