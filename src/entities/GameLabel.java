package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author shanks
 */
public class GameLabel extends JLabel{
    
    public GameLabel(){

    }//fim construtor
    
    @Override 
    public void paintComponent(Graphics gph){
        //super.paintComponent(gph);
        
        Graphics2D graphics = (Graphics2D) gph.create();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        ImageIcon icon = (ImageIcon) getIcon();
        double w = icon.getIconWidth();
        double h = icon.getIconHeight();
        double pImg = w/h;
        double width = this.getWidth();
        double height = this.getHeight();
        double pLabel = width/height;
     
        
        if(pImg<pLabel){
            h=this.getHeight();
            w=h*pImg;
        }else if(pImg > pLabel){
            w=this.getWidth();
            h=w/pImg;
        }else{
            w=this.getWidth();
            h=this.getHeight();
        }//fim if-else
        
        graphics.drawImage(icon.getImage(), 0, 0, (int) w, (int)h, this);               
    }//fim paintComponent
    
}//fim class
