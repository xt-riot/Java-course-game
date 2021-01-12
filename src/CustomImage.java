import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class CustomImage {
    private BufferedImage image;
    protected static final Logger dbg = Logger.getLogger(Panel.class.getName());

    CustomImage() {
        this.image = new BufferedImage(Main.WIDTH,Main.HEIGHT , BufferedImage.TYPE_INT_ARGB);
        this.initiate();
    }

    private void initiate() {
        BufferedImage test = new BufferedImage(Main.WIDTH,Main.HEIGHT , BufferedImage.TYPE_INT_ARGB);
        try {
            test = ImageIO.read(new File("maxresdefault.jpg"));
        } catch (IOException ex) {
            dbg.severe(ex.getMessage());
        }
        Image tmp = test.getScaledInstance(Main.WIDTH, Main.HEIGHT, Image.SCALE_SMOOTH);
        this.image = new BufferedImage(Main.WIDTH, Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = this.image.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
    }

    public BufferedImage getImage() {
        return this.image;
    }
}
