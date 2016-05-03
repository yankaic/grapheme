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
import javax.swing.JLabel;

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
        distance = 255;
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
            JComponent component = object.getComponent();

            component.setOpaque(true);
            Color transparent = new Color(0, 0, 0, 0);
            Graphics graphics = component.getGraphics();
            for (double variable = 255; variable >= 0; variable -= speed) {
                transparent = new Color(0, 0, 0, (int)variable);
                graphics.setColor(transparent);
                graphics.fillRect(0, 0, component.getWidth(), component.getHeight());
                sleep(1000);
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
