package effects;

import entities.GameLabel;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static final boolean FADE_IN = true;//opção fadeIn
    public static final boolean FADE_OUT = false;//opção fadeOut
    private final boolean fade;//opção de efeito escolhida
    

    /**
     * Método construtor da Classe
     *
     * @param object GameLabel : componente alterado
     * @param time long : duração da animação
     * @param fade byte: fade Pode assuimir o valor das constantes FADE_IN e
     * @param finalAlpha float: valor final para o alpha
     * FADE_OUT
     */
    public Fade(GameLabel object, long time, float finalAlpha, boolean fade) {
        this.object = object;
        this.fade = fade;
        distance = finalAlpha;
        //calcula a taxa de atualização do fade da label
        this.speed = (this.distance * 100) / (time * 6);
        //seta o valor alpha inicial da label
        if(fade){//se o efeito for fadeIn, então o valor alpha inicial é 0f
            object.setAlpha(0f);
        }else{//se o efeito for fadeout, então o valor alpha inicial é 1f
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
            
            //realiaza o efeito de fade(in/out) na label
            for(float i=0f; i<=distance;i+=speed){
                float alpha = object.getAlpha();
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
     * @param finalAlpha float: valor final do alpha
     */
    public static void fadeIn(GameLabel component, long time, float finalAlpha) {
        component.setOpaque(true);
        Fade fade = new Fade(component, time, finalAlpha, FADE_IN);
        fade.start();
    }//fim fadeIn

    /**
     * Método que remove toda a opacidade de um objeto gradativamente, por um
     * tempo, time
     *
     * @param component JComponent : componente que terá sua opacidade alterada
     * @param time long : tempo da animação
     * @param finalAlpha float: valor final para alpha
     */
    public static void fadeOut(GameLabel component, long time, float finalAlpha) {
        component.setOpaque(true);
        Fade fade = new Fade(component, time, finalAlpha, FADE_OUT);
        fade.start();
    }//fim fadeOut
}//fim class
