package effects;

import java.awt.Color;
import java.awt.Point;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

/**
 * Classe para movimentacao de qualquer JComponent (JPanel, JLabel, etc.) de uma
 * forma generica e rapida. Possui algumas funcoes estaticas para deixa-la
 * pronta pra uso.
 *
 * @author Yan Kaic
 * @since 2015
 */
public class Translation extends Thread {

    private final JComponent object;
    private final Point startPoint;
    private final Point distance;
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
     * Este construtor apenas configura como a animacao sera realizada. Para que
     * a animacao comece, basta utilizar a funcao start() de Thread.
     *
     * @param object objeto a ser movimentado
     * @param endPoint ponto que o objeto vai ficar quando a animacao terminar.
     * @param time long: tempo da animação em millisegundos
     */
    public Translation(JComponent object, Point endPoint, long time) {
        this.object = object;
        startPoint = object.getLocation();

        distance = new Point();
        distance.x = endPoint.x - startPoint.x;
        distance.y = endPoint.y - startPoint.y;

        hypotenuse = startPoint.distance(endPoint);
        sine = distance.y / hypotenuse;
        cosine = distance.x / hypotenuse;
        
        this.speed = (hypotenuse * 100) / (time * 6);
        System.out.println("H="+hypotenuse+" S="+speed);
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

            object.setBorder(BorderFactory.createLineBorder(Color.red, 2));
            sleep(500);

            double adjacentCateto =  (cosine * speed) + startPoint.x;
            double oppositiveCateto =  (sine * speed) + startPoint.y;
            
            /*for (int hoop = 0; hoop < hypotenuse; hoop+=speed) {
                double x = adjacentCateto + object.getX();
                double y = oppositiveCateto + object.getY();
                object.setLocation((int)x, (int)y);
                sleep(100/6);
            }//fim for
            */
            //Vai aumentando a distancia e descobrindo seus pontos x e y.
            for (int variableHypotenuse = 0; variableHypotenuse < hypotenuse; variableHypotenuse += speed) {

                adjacentCateto = (int) (cosine * variableHypotenuse) + startPoint.x;
                oppositiveCateto = (int) (sine * variableHypotenuse) + startPoint.y;
                object.setLocation((int)adjacentCateto, (int)oppositiveCateto);
                sleep(20);

            }
            //coloca o objeto no ponto final que foi pedido.
            adjacentCateto = (cosine * hypotenuse) + startPoint.x;
            oppositiveCateto = (sine * hypotenuse) + startPoint.y;
            object.setLocation((int)adjacentCateto, (int)oppositiveCateto);

            sleep(500);
            object.setBorder(BorderFactory.createEmptyBorder());
            if (self) {
                aniMutex.release();
                anitag = false;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Translation.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Esta funcao permite que um objeto seja movimento sem precisar instanciar
     * o objeto animacao dentro do seu codigo. Basta inserir o objeto e ponto
     * final desejado. Nota: observe a velocidade antes de usar esta funcao.
     *
     * @param objeto objeto a ser movido
     * @param destino ponto que deseja que o objeto fique depois da animacao.
     * @param time long : tempo da animação em milissegundos
     */
    public static void moveObject(JComponent objeto, Point destino, long time) {
        Translation animacao = new Translation(objeto, destino, time);
        animacao.start();
    }//fim moveObject

}//fim class
