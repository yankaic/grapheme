package effects;

import entities.FadeComponent;
import entities.GameLabel;
import java.awt.Color;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.embed.swing.JFXPanel;
import javafx.util.Duration;

/**
 *
 * @author shanks
 */
public class Fade extends Thread {

    public static final Semaphore aniMutex = new Semaphore(1);
    private static boolean anitag = false;
    private static final Semaphore mutex = new Semaphore(1);

    public static final boolean FADE_IN = true;//opção fadeIn
    public static final boolean FADE_OUT = false;//opção fadeOut
    private final boolean fade;//opção de efeito escolhida

    //Componente (JAVAFX) responsável pela animação de fade
    private FadeTransition fadeTransition;

    /**
     * Método construtor da Classe
     *
     * @param object GameLabel : componente alterado
     * @param time long : duração da animação
     * @param fade byte: fade Pode assuimir o valor das constantes FADE_IN e
     * @param finalAlpha float: valor final para o alpha FADE_OUT
     */
    public Fade(FadeComponent object, long time, float finalAlpha, boolean fade) {
        this.fade = fade;
        fadeTransition = new FadeTransition(Duration.millis(time), object.getFadeBackground());
        
        if(fade){//fade in
            fadeTransition.setFromValue(object.getAlpha());
            fadeTransition.setToValue(finalAlpha);
            object.setAlpha(finalAlpha);
        }else{//fade out
            fadeTransition.setFromValue(object.getAlpha());
            fadeTransition.setToValue(0.0f);
            object.setAlpha(finalAlpha);
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
            
            fadeTransition.play();//inicia a animação de fade

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
     * @return fade Fade
     */
    public static Fade fadeIn(FadeComponent component, long time, float finalAlpha) {
        Fade fade = new Fade(component, time, finalAlpha, FADE_IN);
        fade.start();
        return fade;
    }//fim fadeIn

    /**
     * Método que remove toda a opacidade de um objeto gradativamente, por um
     * tempo, time
     *
     * @param component JComponent : componente que terá sua opacidade alterada
     * @param time long : tempo da animação
     * @param finalAlpha float: valor final para alpha
     * @return fade Fade
     */
    public static Fade fadeOut(FadeComponent component, long time,  float finalAlpha) {
        Fade fade = new Fade(component, time, finalAlpha, FADE_OUT);
        fade.start();
        return fade;
    }//fim fadeOut
}//fim class

