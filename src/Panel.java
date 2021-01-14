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

    protected void fadeOut(int fast) {
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(e -> {
            if (this.panels == this.getComponentCount() && this.isShown) {
                this.startRenderingImage(Main.WIDTH);
                System.out.println(this.getClass().getName() + " has completed fading out all its components. Starting fading out the background image.");
                //this.parent.choosePlayerName();
                this.timerForAnimations.stop();
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
        return this.hasbuttons;
    }
    public int getImagePosition() {
        return this.imgCurrentPosition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, imgCurrentPosition, 0, null);
    }
}

/*
abstract public class Panel extends JPanel {
    protected JFrame frame;
    protected static final Logger dbg = Logger.getLogger(Panel.class.getName());

    protected BufferedImage img;
    protected int alpha;
    protected int imgCurrentPosition;
    protected boolean readyToLeave;
    protected boolean safeRemove;

    protected int components;

    private timerForAnimations timerForAnimations;
    private ActionListener action;

    private final int imageYPosition = 150;

    protected Panel(JFrame frameID) {
        this.frame = frameID;
        this.action = e -> {};
        this.timerForAnimations = new timerForAnimations(1, action);
        this.readyToLeave = false;
        this.components = 0;
        this.imgCurrentPosition = -(Main.WIDTH);
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(0,0,0, 255));
        this.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
    }

    protected void animations() {
        if(!this.readyToLeave) {
            this.imgCurrentPosition = -(800);
            this.action = e -> {
                this.imgCurrentPosition += frame.getWidth()/80;
                if(this.imgCurrentPosition > imageYPosition) this.imgCurrentPosition = imageYPosition;
                paintImg(imageYPosition);
            };
        }
        else {
            this.imgCurrentPosition = imageYPosition;
            this.action = e -> {
                imgCurrentPosition += frame.getWidth()/80;
                paintImg(frame.getWidth() * 2);
            };
        }//
        this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
        this.timerForAnimations.addActionListener(action);
        repaint();
        //this.timerForAnimations = new timerForAnimations(1, action);
        this.timerForAnimations.start();
    }

    private void paintImg(int end) {
        repaint();

        System.out.println(readyToLeave + " ++ "+this.imgCurrentPosition + " // " + end);
        if(!this.readyToLeave) {
            if(this.imgCurrentPosition >= end && end == imageYPosition) {
                this.nextRender();
                this.timerForAnimations.stop();
            }
            else if ( this.imgCurrentPosition < end && end > imageYPosition) {
                this.timerForAnimations.stop();
                this.action = e -> {
                    imgCurrentPosition += frame.getWidth()/80;
                    paintImg(frame.getWidth() * 2);
                };
                this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
                this.timerForAnimations.addActionListener(action);
                this.timerForAnimations.setDelay(1);
                this.timerForAnimations.start();
            }
            else if ( this.imgCurrentPosition >= end && end > imageYPosition) {
                System.out.println(imgCurrentPosition);
                this.timerForAnimations.stop();
                goNext();
            }
        }
        else {
            if(this.timerForAnimations.getDelay() == 1) {
                this.nextRender();
                this.imgCurrentPosition = imageYPosition;
                this.timerForAnimations.stop();
                this.action = e -> {
                    paintImg(frame.getWidth() * 2);
                };
                this.timerForAnimations.removeActionListener(this.timerForAnimations.getActionListeners()[0]);
                this.timerForAnimations.addActionListener(action);
                this.timerForAnimations.setDelay(50);
                this.timerForAnimations.start();
            }//

        }
    }

    abstract public void startRendering();
    abstract public void unRender();
    abstract protected void nextRender();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, imgCurrentPosition, 0, null);

    }
    abstract void goNext();


    protected void getImg() {
        BufferedImage TestFiles.test = new BufferedImage(Main.WIDTH,Main.HEIGHT , BufferedImage.TYPE_INT_ARGB);
        try {
            TestFiles.test = ImageIO.read(new File("maxresdefault.jpg"));
        } catch (IOException ex) {
            dbg.severe(ex.getMessage());
        }
        Image tmp = TestFiles.test.getScaledInstance(Main.WIDTH, Main.HEIGHT, Image.SCALE_SMOOTH);
        this.img = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = this.img.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    }

}//*/