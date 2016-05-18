/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.awt.Dimension;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author shanks
 */
public class FadeComponent extends JFXPanel {
    Rectangle background;
    float alpha;
    
    /**
     * Construtor da classe
     */
    public FadeComponent(Dimension backgroundDimension){
        super(); 
        this.setOpaque(false);
        background = new Rectangle(0,0, backgroundDimension.getWidth(), backgroundDimension.getHeight());
        this.setScene(createScene());
        alpha=0.0f;        
    }//fim construtor
    
    private Scene createScene(){
        Group  root  =  new  Group();
               
        Scene  scene  =  new  Scene(root);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
       
        background.setFill(javafx.scene.paint.Color.BLACK);
        
        root.getChildren().add(background);
        return scene;
    }//fim createScene  
    
    public Rectangle getFadeBackground(){
        return background;
    }
    
    public void setAlpha(float alpha){
        this.alpha=alpha;
    }
    
    public float getAlpha(){
        return alpha;
    }
            
}//fim class
