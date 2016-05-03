/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Yan Kaic
 */
public class Letter extends JLabel {
    
    public Letter(){
        this("A");
    }
    public Letter(String name){
        super();
        setName(name);
        init();
    }

    private void init() {
        String letter = getName().toLowerCase();
        ImageIcon icon = new ImageIcon(getClass().getResource("/letters/"+letter+"/uppercase/letter.png"));
        setIcon(icon);
        setSize(icon.getIconWidth(),icon.getIconHeight());
    }
    
    
}
