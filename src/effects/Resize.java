package effects;

import entities.GameObject;
import java.awt.Dimension;
import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 * Classe para movimentacao de qualquer JComponent (JPanel, JLabel, etc.) de uma
 * forma generica e rapida. Possui algumas funcoes estaticas para deixa-la
 * pronta pra uso.
 *
 * @author Yan Kaic
 * @since 2015
 */
public class Resize extends Thread {

  private final GameObject object;
  private final Dimension startPoint;
  private final Dimension distance;
  private final double sine;
  private final double cosine;
  private final double hypotenuse;
  private final double speed;

  /**
   *
   */
  public static final Semaphore aniMutex = new Semaphore(1);
  private static boolean anitag = false;
  private static final Semaphore mutex = new Semaphore(1);

  /**
   * Construtor padrao para a animacao de um objeto. <br>
   * Este construtor apenas configura como a animacao sera realizada. Para que a
   * animacao comece, basta utilizar a funcao start() de Thread.
   *
   * @param object objeto a ser movimentado
   * @param endPoint ponto que o objeto vai ficar quando a animacao terminar.
   * @param time long: tempo da animação em millisegundos
   */
  public Resize(GameObject object, Dimension endPoint, long time) {
    this.object = object;
    startPoint = object.getSize();

    distance = new Dimension();
    distance.width = endPoint.width - startPoint.width;
    distance.height = endPoint.height - startPoint.height;
    
    hypotenuse = Math.sqrt(Math.pow(distance.width, 2) + Math.pow(distance.height, 2));
    sine = distance.height / hypotenuse;
    cosine = distance.width / hypotenuse;
    
    this.speed=(hypotenuse*100)/(time*6);
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
      }
      mutex.release();

      double adjacentCateto=cosine*speed,
             oppositiveCateto=sine*speed;

      //Vai aumentando a distancia e descobrindo seus pontos x e y.
      for (double variableHypotenuse = 0; variableHypotenuse < hypotenuse; variableHypotenuse += speed) {

        double w =adjacentCateto + object.getWidthF();
        double h =oppositiveCateto + object.getHeightF();
        object.setSize(w, h);
        sleep(100/6);
      }
      //coloca o objeto no ponto final que foi pedido.
      adjacentCateto =  (cosine * hypotenuse) + startPoint.width;
      oppositiveCateto =  (sine * hypotenuse) + startPoint.height;
      //object.paintImmediately(object.getLocation().x, object.getLocation().y,(int) adjacentCateto,(int) oppositiveCateto);

      if (self) {
        aniMutex.release();
        anitag = false;
      }
    }
    catch (InterruptedException ex) {
      Logger.getLogger(Translation.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Esta funcao permite que um objeto seja redimensionado sem precisar instanciar o
   * objeto animacao dentro do seu codigo. Basta inserir o objeto e novo tamanho
   * desejado. Nota: observe a velocidade antes de usar esta funcao.
   *
   * @param objeto JComponent : objeto a ser redimensionado
   * @param newSize Dimension: novo tamanho do objeto
   * @param time  long : tempo da animação em milissegundos
   * 

   */
  public static void resizeObject(JComponent objeto, Dimension newSize, long time) {
    Resize animacao = new Resize(new GameObject(objeto), newSize, time);
    animacao.start();
  }//fim moveObject
  
  public static Resize resize (JComponent objeto, Dimension newSize, long time) {
    Resize animacao = new Resize(new GameObject(objeto), newSize, time);
    animacao.start();
    return animacao;
  }//fim moveObject


}//fim class
