package effects;

import entities.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author shanks
 */
public class Fade extends Thread {

    public static final Semaphore aniMutex = new Semaphore(1);
    private static boolean anitag = false;
    private static final Semaphore mutex = new Semaphore(1);

    private GameObject object;//objeto alterado
    private double speed;    //taxa de animação
    private double distance; //distância entre a cor atual do objeto e a anterior
    private static final byte FADE_IN = 1;
    private static final byte FADE_OUT = -1;
    private byte fade;

    /**
     * Método construtor da Classe
     *
     * @param object GameObject : componente alterado
     * @param time long : duração da animação
     * @param fade byte: fade Pode assuimir o valor das constantes FADE_IN e
     * FADE_OUT
     */
    public Fade(GameObject object, long time, byte fade) {
        this.object = object;
        this.fade = fade;
        
        if(this.fade==FADE_IN){
            distance=255-this.object.getColorBackgroud().getAlpha();
        }else{
            distance=this.object.getColorBackgroud().getAlpha();
        }
        this.speed = (this.distance * 100) / (time * 6);
    }//fim construtor

    @Override
    public void run() {
        try {
            boolean self = false;
            mutex.acquire();
            if (!anitag) {
                aniMutex.acquire();
                anitag = true;
                self = true;
            }//fim if
            mutex.release();
            
         
            object.getComponent().setOpaque(true);
            for (double variable = 0; variable < distance; variable+=speed) {
                double alpha = fade*speed + this.object.getAlpha();
              // object.setAlpha(alpha);
                //System.out.println("alpha"+alpha + " -- "+ fade*speed  + " )) " + variable + " " + distance);
                Color c = new Color( 0, 0,0,3);  
                //object.setBackground(c);
                object.getComponent().setBackground(c);
                object.getComponent().repaint();
                //System.out.println("******* "+ object.getComponent().getBackground().getAlpha());
                sleep(200);
            }//fim for
          //  this.object.setAlpha(0.0);
            if (self) {
                aniMutex.release();
                anitag = false;
            }//fim if

        } catch (InterruptedException ex) {
            Logger.getLogger(Translation.class.getName()).log(Level.SEVERE, null, ex);
        }//fim try-catch
    }//fim run

    /**
     * Método que adiciona opacidade a um objeto, por um tempo, time
     *
     * @param component JComponent : componente que terá sua opacidade alterada
     * @param time long : tempo da animação
     */
    public static void fadeIn(JComponent component, long time) {
        component.setOpaque(true);
        Fade fade = new Fade(new GameObject(component), time, FADE_IN);
        fade.start();
    }//fim fadeIn

    /**
     * Método que remove toda a opacidade de um objeto gradativamente, por um
     * tempo, time
     *
     * @param component JComponent : componente que terá sua opacidade alterada
     * @param time long : tempo da animação
     */
    public static void fadeOut(JComponent component, long time) {
        component.setOpaque(true);
        Fade fade = new Fade(new GameObject(component), time, FADE_OUT);
        fade.start();
    }//fim fadeOut
}//fim class
