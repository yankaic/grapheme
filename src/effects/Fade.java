package effects;

import entities.FadeLabel;
import entities.GameLabel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author shanks
 */
public class Fade extends Thread {

    public static final Semaphore aniMutex = new Semaphore(1);
    private static boolean anitag = false;
    private static final Semaphore mutex = new Semaphore(1);

    private final GameLabel object;//objeto alterado
    private final double speed;    //taxa de animação
    private final double distance; //distância entre a cor atual do objeto e a anterior
    private static final boolean FADE_IN = true;
    private static final boolean FADE_OUT = false;
    private final boolean fade;
    

    /**
     * Método construtor da Classe
     *
     * @param object GameObject : componente alterado
     * @param time long : duração da animação
     * @param fade byte: fade Pode assuimir o valor das constantes FADE_IN e
     * FADE_OUT
     */
    public Fade(GameLabel object, long time, boolean fade) {
        this.object = object;
        this.fade = fade;
        distance = 1;
        this.speed = (this.distance * 100) / (time * 6);
        
        if(fade){
            object.setAlpha(0f);
        }else{
            object.setAlpha(1f);
        }//fim if-else
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
            
            for(float i=0f; i<=distance;i+=speed){
                float alpha = object.getAlpha();
                System.out.println(alpha);
                if (fade) {//incrementa o fade (fadeIn)
                    alpha += speed;                       
                } else{    //decrementa o fade (fadeout)
                    alpha -= speed;
                }//fim if-else

                //limite dos efeitos de fade
                if (alpha < 0) {
                    alpha = 0;
                } else if (alpha > 1) {
                    alpha = 1;
                }//fim if-else
                object.setAlpha(alpha);
                sleep(100/6);
           }//fim for
       
            System.out.println(object.getAlpha());
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
    public static void fadeIn(GameLabel component, long time) {
        component.setOpaque(true);
        Fade fade = new Fade(component, time, FADE_IN);
        fade.start();
    }//fim fadeIn

    /**
     * Método que remove toda a opacidade de um objeto gradativamente, por um
     * tempo, time
     *
     * @param component JComponent : componente que terá sua opacidade alterada
     * @param time long : tempo da animação
     */
    public static void fadeOut(GameLabel component, long time) {
        component.setOpaque(true);
        Fade fade = new Fade(component, time, FADE_OUT);
        fade.start();
    }//fim fadeOut
}//fim class
