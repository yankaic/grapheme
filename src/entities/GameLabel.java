package entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author shanks
 */
public class GameLabel extends JLabel {

    private boolean fadein;
    private float alpha;

    public GameLabel() {
    }//fim construtor

    @Override
    public void paintComponent(Graphics gph) {
        super.paintComponent(gph);

        Graphics2D graphics = (Graphics2D) gph.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getIcon() != null) {
            resizeIcon(graphics);
        }//fim if
    }

    private void resizeIcon(Graphics2D graphics) {
        ImageIcon icon = (ImageIcon) getIcon();
        double w = icon.getIconWidth();
        double h = icon.getIconHeight();
        double pImg = w / h;
        double width = this.getWidth();
        double height = this.getHeight();
        double pLabel = width / height;

        if (pImg < pLabel) {
            h = this.getHeight();
            w = h * pImg;
        } else if (pImg > pLabel) {
            w = this.getWidth();
            h = w / pImg;
        } else {
            w = this.getWidth();
            h = this.getHeight();
        }//fim if-else

        graphics.drawImage(icon.getImage(), 0, 0, (int) w, (int) h, this);
    }   
    
    
    public void setAlpha(float value) {
        if (alpha != value) {
            float old = alpha;
            alpha = value;
            firePropertyChange("alpha", old, alpha);
            repaint();
        }
    }

    public float getAlpha() {
        return alpha;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return getIcon() == null ? new Dimension(300,300) : new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        super.paint(g2d);
        g2d.dispose();
    }


}//fim class
