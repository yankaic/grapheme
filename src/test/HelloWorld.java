/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javax.swing.Timer;

public class HelloWorld {
 static    int x=0;
 static Timer t;
 
    public static void main(String[] args) {
        
        //HelloWorld helloWorld = new HelloWorld();
       // KeyFrame k = new KeyFrame(3000, helloWorl
       
       t = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               x+=1;
                System.out.println("x="+x);
               if(x>10){
                   t.stop();
               }
            }
        }
       
       );
       t.start();
        try {

            Thread.sleep(3000);

        } catch (Exception exc) {

        }
        System.out.println("AQUI");
    }

}
