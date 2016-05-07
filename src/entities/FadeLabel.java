/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;

public class FadeLabel extends JLabel {

    private float alpha;//intensidade do canal alpha do component
    private BufferedImage background;//imagem de efeito

    /**
     * Construtor da classe
     *  - Seta o background da label e sua orientação
     */
    public FadeLabel() {
        try {
            background = ImageIO.read(getClass().getResource(File.separator + "images" + File.separator + "black.png"));
        } catch (Exception e) {
        }//fim try-catch
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        setAlpha(1f);
    }//fim construtor

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
        return background == null ? super.getPreferredSize() : new Dimension(background.getWidth(), background.getHeight());
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        super.paint(g2d);
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
