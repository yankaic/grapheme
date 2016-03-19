package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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
        graphics.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);               
    }//fim paintComponent
    
}//fim class
