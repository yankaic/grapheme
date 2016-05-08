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

    private float alpha;//valor do alpha da label

    /**
     * Construtor da classe
     */
    public GameLabel() {
        alpha=1f;
    }//fim construtor

    @Override
    public void paintComponent(Graphics gph) {
        super.paintComponent(gph);

        Graphics2D graphics = (Graphics2D) gph.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //redimensiona o icone da label, se existir
        if (getIcon() != null) {

            resizeIcon(graphics);
        }//fim if
    }//fim paintComponent

    /**
     * Método que redimensiona o icone da label, proporcionalmente ao tamanho da label
     * @param graphics 
     */
    private void resizeIcon(Graphics2D graphics) {
        ImageIcon icon = (ImageIcon) getIcon();
        //tamanho atual do icone
        double w = icon.getIconWidth();
        double h = icon.getIconHeight();
        
        //proporção do icone
        double pImg = w / h;
        
        //tamanho da label
        double width = this.getWidth();
        double height = this.getHeight();
        
        //proporção da label
        double pLabel = width / height;

        //calcula o novo tamanho do icone com base em sua proporção e a proporção da label
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
        
        //redesenhando o icone
        graphics.drawImage(icon.getImage(), 0, 0, (int) w, (int) h, this);
    } //fim risezeIcon
    
    /**
     * Método que modifica o valor de alpha da label e atualiza sua visualização
     * @param value float : novo valor do alpha
     */
    public void setAlpha(float value) {
        if (alpha != value) {
            float old = alpha;
            alpha = value;
            firePropertyChange("alpha", old, alpha);//realiza a transição dos valores de alpha
            repaint();//redesenha a label
        }//fim if
    }//fim setAlpha

    /**
     * Método que retorna o valor de alpha da label
     * @return alpha float
     */
    public float getAlpha() {
        return alpha;
    }//fim getAlpha
    
    /**
     * Método que retorna o tamanho da label
     * @return Dimension 
     */
    @Override
    public Dimension getPreferredSize() {
        return getIcon() == null ? new Dimension(300,300) : new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
    }//fim getPreferredSize

    /**
     * Método que desenha a label com suas propriedades de alpha
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
        super.paint(g2d);
        g2d.dispose();
    }//fim paint


}//fim class
