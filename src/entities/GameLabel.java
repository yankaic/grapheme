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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import static javax.swing.SwingConstants.CENTER;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

/**
 *
 * @author shanks
 */
public class GameLabel extends JLabel {

    private float alpha;//valor do alpha da label
    private Icon icon;
    private boolean center;
    private double wi;
    private double hi;

    /**
     * Construtor da classe
     */
    public GameLabel() {
        alpha = 1f;
    }//fim construtor
    
    @Override
    public void setIcon(Icon icon){
        super.setIcon(null);
        this.icon=icon;
    }
    
    public Icon getImage(){
        return icon;
    }

    public void setCenterAlignment(boolean center){
        this.center=center;
    }
    @Override
    public void paintComponent(Graphics gph) {
        super.paintComponent(gph);

        Graphics2D graphics = (Graphics2D) gph.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //redimensiona o icone da label, se existir
        if (icon != null) {
            resizeIcon(graphics);
        }//fim if
        if (getComponentCount() != 0) {
            resizeComponents(graphics);
        }//fim if
    }//fim paintComponent

    /**
     * Método que redimensiona o icone da label, proporcionalmente ao tamanho da
     * label
     *
     * @param graphics
     */
    private void resizeIcon(Graphics2D graphics) {
        ImageIcon iconImage = (ImageIcon) icon;
        //tamanho atual do icone
        wi = icon.getIconWidth();
        hi = icon.getIconHeight();

        //proporção do icone
        double pImg = wi / hi;

        //tamanho da label
        double width = this.getWidth();
        double height = this.getHeight();

        //proporção da label
        double pLabel = width / height;

        //calcula o novo tamanho do icone com base em sua proporção e a proporção da label
        if (pImg < pLabel) {
            hi = this.getHeight();
            wi = hi * pImg;
        } else if (pImg > pLabel) {
            wi = this.getWidth();
            hi = wi / pImg;
        } else {
            wi = this.getWidth();
            hi = this.getHeight();
        }//fim if-else

        //redesenhando o icone
        graphics.drawImage(iconImage.getImage(), getWidth()/2-icon.getIconWidth()/2, 0, (int) wi, (int) hi, this);
    } //fim risezeIcon

    public double getWi() {
        return wi;
    }

    public double getHi() {
        return hi;
    }
    

    
    /**
     * Método que redimensiona os componentes da label, proporcionalmente ao
     * tamanho da label
     *
     * @param graphics
     */
    private void resizeComponents(Graphics2D graphics) {
        for (int i = 0; i < getComponentCount(); i++) {
            GameLabel component = (GameLabel) getComponent(i);
            //tamanho atual do icone
            double w = component.getWidth();//getIconWidth();
            double h = component.getHeight();//getIconHeight();

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
            component.setSize((int)w, (int)h);
            
            if(center){
                component.setLocation((this.getWidth()/2) - (int)component.getWidth()/2, 
                                      (this.getHeight()/2) - (int)component.getHi()/2);
                System.out.println(getLocation());
            }
            
            //component.repaint();    
        }//fim for
    } //fim risezeComponents

    /**
     * Método que modifica o valor de alpha da label e atualiza sua visualização
     *
     * @param value float : novo valor do alpha
     */
    public void setAlpha(float value) {
        if (alpha != value) {
            float old = alpha;
            alpha = value;
            firePropertyChange("alpha", old, alpha);//realiza a transição dos valores de alpha
//            repaint();//redesenha a label
        }//fim if
    }//fim setAlpha

    /**
     * Método que retorna o valor de alpha da label
     *
     * @return alpha float
     */
    public float getAlpha() {
        return alpha;
    }//fim getAlpha

    /**
     * Método que retorna o tamanho da label
     *
     * @return Dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return getIcon() == null ? new Dimension(300, 300) : new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
    }//fim getPreferredSize

  

}//fim class
