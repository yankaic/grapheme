package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author shanks
 */
public class GameLabel extends JLabel {

    private boolean fadein;

    public GameLabel() {

    }//fim construtor

    @Override
    public void paintComponent(Graphics gph) {
        //super.paintComponent(gph);

        Graphics2D graphics = (Graphics2D) gph.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getIcon() != null) {
            resizeIcon(graphics);
        }
        if (fadein) {
            fadein(graphics);
        }
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

    private void fadein(Graphics2D graphics) {
        new Thread() {
            @Override
            public void run() {
                Color color;
                for (int i = 0; i < 255; i++) {
                    try {
                        color = new Color(0, 0, 0, i);
                        graphics.setPaint(color);
                        graphics.fillRect(0, 0, getWidth(), getHeight());
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameLabel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

}//fim class
