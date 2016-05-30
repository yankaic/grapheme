/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package effects;

import entities.FadeComponent;
import entities.GameLabel;
import entities.GameObject;
import graphemes.Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.BooleanControl.Type;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Control;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import view.LowerCaseLetterAnimation;
import view.SwipeView;
import entities.Letter;

/**
 *
 * @author shanks
 */
public class LetterTransition extends Thread {

    //letra cuja transição será animada
    private Letter letter;

    //typo da transição
    private boolean type;
    public static boolean UPPER_CASE = true;
    public static boolean LOWER_CASE = false;

    //tamanho da tela
    private Dimension viewDimension;

    //posição do centro da tela
    private Point finalPosition;

    //posição inicial da label sendo animada
    private Point initialPosition;

    //tamanho inicial da label sendo animada
    private Dimension initialDimension;

    //tamanho final da label sendo animada
    private Dimension finalDimension;

    //audio da animação 
    private Clip clip;

    //path do audio
    private String audioSource;

    //objeto responsável pelas translações dos componentes
    private static Translation tranlation;

    //objeto responsável pelos redimensionamentos dos componentes
    private static Resize resize;

    //imagens apresentadas na animação
    private GameLabel image[];

    //objeto responsável pela animação de fade de componentes
    private static Fade fade;

    //variável que verifica de a animação está rodando
    private boolean isRunning;

    //variável que armazena o tempo atual do audio
    private long audioTime;

    //variável que decide se oo audio irá ser reproduzido ou não
    private boolean enabledAudio;

    /**
     * Método construtor da classe de animação da transição de uma letra
     *
     * @param letter Letter : letra que será animada
     * @param type boolean : tipo da animação pode assumir os valores:
     * UPPER_CASE ou LOWER_CASE
     * @param viewDimension Dimension: tamanho da janela
     */
    public LetterTransition(Letter letter, Dimension viewDimension, boolean type) {
        this.letter = letter;//letra sendo animada
        this.type = type;//tipo da animação
        this.audioTime = -1;//tempo inicial do audio em microsegundos
        //configurações default
        initialPosition = new Point(770, 150); //posição inicial das labels sendo animadas
        initialDimension = new Dimension(0, 0); //dimensão inicial da label
        finalDimension = new Dimension(255, 369);//dimensão final da label
        this.viewDimension = viewDimension;
        enabledAudio = true;//habilita a reprodução de audio
    }//fim construtor

    /**
     * Construtor vazio do objeto
     */
    public LetterTransition() {
    }//fim construtor

    /**
     * Método que faz a animação
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void run() {
        try {
            isRunning = true;
            if (type) {//animação das letras maiúsculas

            } //animação das letras minúsculas
            else {
                //diretório dos exemplos
                URI uri = new URI(letter.getLowerCasePath() + "examples");
                File directory = new File(uri);
                //quantidade de exemplos
                int maxImages = directory.list().length;
                //vetor com os exemplos
                image = new GameLabel[maxImages];
                //caminho para as imagens dos exemplos
                String path = letter.getLowerCasePath() + "examples" + Main.BAR;

                //laço que inicializa as labels das imagens e seta seus icones
                for (int countImages = 0; countImages < maxImages; countImages++) {
                    //icone do exemplo        
                    String fullPath = path + Integer.toString(countImages + 1) + Main.BAR + "image.png";//caminho da imagem
                    ImageIcon icon = new ImageIcon(new URL(fullPath));//pegando a imagem do exemplo

                    //criando a label para a imagem
                    image[countImages] = new GameLabel();
                    image[countImages].setIcon(icon);//setando o icone da label
                }//fim for

                //laço que posiciona as imagens no centro da tela
                for (int countImages = 0; countImages < image.length; countImages++) {
                    //a primeira imagem parte da televisão na tela e se posiciona no centro da imagem, 
                    // depois disso, todas as outras imagens são setadas ao fundo dessa primeira imagem
                    //  centralizada
                    if (countImages == 0) {
                        //posição e dimensão iniciais da label
                        image[countImages].setLocation(initialPosition);
                        image[countImages].setSize(initialDimension);

                        //calculando a posição final e a dimensão final
                        finalDimension = new Dimension(image[countImages].getImage().getIconWidth(),
                                image[countImages].getImage().getIconHeight());
                        finalPosition = new Point((int) (viewDimension.width / 2) - (finalDimension.width / 2),
                                (int) (viewDimension.height / 2) - (finalDimension.height / 2));

                        //adicionando o exemplo na janela
                        SwipeView.addTransitionLabels(image[countImages], countImages);

                        //animando a translação e redimensionamento da label
                        tranlation = Translation.move(image[countImages], finalPosition, 2000);
                        resize = Resize.resize(image[countImages], finalDimension, 2000);
                        // sleep(2500);
                    } else {
                        //posição das demais imagens
                        while (!image[0].getLocation().equals(finalPosition)) {
                            sleep(10);
                        }//fim while
                        ///System.out.println("aqui");
                        image[countImages].setLocation(finalPosition);
                        image[countImages].setSize(finalDimension);

                        //adicionando o exemplo na janela
                        SwipeView.addTransitionLabels(image[countImages], countImages);

                    }//fim if-else
                }//fim for

                //laço que anima as imagens, juntamento com o audio e sua reorganização na tela
                for (int countImages = 0; image != null && countImages < image.length; countImages++) {

                    /*
                        A tela será dividida em três campos, conforme imagem abaixo.
                        Onde casa número corresponde à numeração do exemplo, em relação
                        a seu mod por 3.

                         - - - - - - - 
                         - - - - - - -
                         - 1 - 0 - 2 -
                         - - - - - - -
                         - - - - - - -
                     */
                    if (enabledAudio) {
                        audioSource = path + Integer.toString(countImages + 1) + Main.BAR + "audio.aiff";
                        loadClip(audioSource);
                        clip.start();
                    }//fim if
                    sleep(3000);
                    //calculando o delta de deslocamento da imagem para sua posicao final
                    int dw = image[countImages].getImage().getIconWidth() + 20;

                    //diferenciando o delta de acordo com o indice da imagem
                    //serão colocados 3 imagem na tela por vez
                    int delta = ((countImages + 1) % 3 == 1) ? -dw //caso o indice da imagem mod 3 seja igual a 1 (esquerda)
                            : ((countImages + 1) % 3 == 2) ? dw //caso o indice da imagem mod 3 seja igual a 2 (direita)
                                    : 0; //caso o indice da imagem mod 3 seja igual a 0 (centro)

                    //calcula a posicao final da imagem na animação
                    //   a imagem pode deslocar apenas no eixo x, a uma variação delta
                    int x = image[countImages].getLocation().x + delta;
                    int y = image[countImages].getLocation().y;
                    finalPosition = new Point(x, y);

                    //posicionando a imagem no local correto
                    if (countImages != image.length - 1) {
                        tranlation = Translation.move(image[countImages], finalPosition, 2000);
                    }//fim if
                    sleep(2000);
                }//fim for                
                sleep(5000);
            } //fim if-else
        } catch (InterruptedException | URISyntaxException | MalformedURLException ex) {
            Logger.getLogger(LetterTransition.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(LetterTransition.class.getName()).log(Level.SEVERE, null, ex);
        }//fim try-catch
        isRunning = false;
        Timer closePanelAnimation = new Timer(1500, (ActionEvent e) -> {
            if (!isRunning) {
                LowerCaseLetterAnimation.getCloseButton().doClick();
            }//fim if
        });//fim timer
        closePanelAnimation.setRepeats(false);
        closePanelAnimation.start();
    }//fim run

    /**
     * Método que realiza a transição de uma letra minuscúla a animação de uma
     * letra minúscula é mostrar uma label com a imagem dos objetos da letra e o
     * áudio das palavras mostradas nas imagens
     *
     * @param letter Letter : letra que será animada
     * @param viewSize Point : tamanho (width, height) da tela
     */
    public static void lowerCaseLetter(Letter letter, Dimension viewDimension) {
        new LetterTransition(letter, viewDimension, LOWER_CASE).start();
    }//fim lowerCaseLetter

    /**
     * Método que realiza a transição de uma letra maiúscula a animação de uma
     * letra maiúscula é mostrar um video da pronuncia de uma palavra
     *
     * @param letter Letter : letra que será animada
     * @param viewSize Point: tamanho (width, height) da tela
     */
    public static void upperCaseLetter(Letter letter, Dimension viewDimension) {
        new LetterTransition(letter, viewDimension, UPPER_CASE).start();
    }//fim upperCaseLetter

    /**
     * Método que carrega um audio para ser executado
     *
     * @param audioPath String : caminho do audio
     * @throws LineUnavailableException
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws URISyntaxException
     */
    private void loadClip(String audioPath) throws LineUnavailableException, IOException, UnsupportedAudioFileException, URISyntaxException {
        File audioFile = new File(new URI(audioPath));
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }//fim loadClip

    /**
     * Método que pausa a animação
     */
    public void pause() {
        tranlation.suspend();
        resize.suspend();
        if (clip.isRunning()) {
            audioTime = clip.getMicrosecondLength();
            clip.stop();
        }//fim if
        this.suspend();
    }//fim pause

    /**
     * Método que inicia ou retoma a animação
     */
    public void play() {
        this.resume();
        resize.resume();
        tranlation.resume();
        if (clip != null && audioTime > 0) {
            clip.setMicrosecondPosition(audioTime);
            clip.start();
            audioTime = -1;
        }//fim if
    }//fim play

    /**
     * Método que reinicia a animação
     *
     * @return LetterTransition
     */
    public LetterTransition replay() {
        LetterTransition letterTransition = new LetterTransition(letter, viewDimension, type);
        letterTransition.start();
        for (int countImage = 0; image != null && countImage < image.length; countImage++) {
            if (image[countImage] != null) {
                image[countImage].setVisible(false);
            }//fim if
        }//fim for
        this.stop();
        return letterTransition;
    }//fim replay

    /**
     * Método que habilita o audio da animação
     */
    public void enableAudio() {
        enabledAudio = true;
    }//fim enableAudio

    /**
     * Método que desabilita o audio da animação
     */
    public void disableAudio() {
        enabledAudio = false;
    }//fim disableAudio

    /**
     * Método que encerra a animação
     */
    public void close() {
        if (tranlation != null) {
            tranlation.stop();
        }//fim if
        if (resize != null) {
            resize.stop();
        }//fim if
        for (int countImage = 0; image != null && countImage < image.length; countImage++) {
            if (image[countImage] != null) {
                image[countImage].setVisible(false);
            }//fim if
        }//fim for
        audioTime = -1;
        this.stop();
    }//fim close

    /**
     * Método que inicia uma animação dependendo da visibilidade do painel
     * lowerCaseLetterAnimation
     *
     * @param fadeComponent FadeComponent
     * @param fadeScale float
     * @param visibility boolean
     */
    public static void fade(FadeComponent fadeComponent, float fadeScale, boolean visibility) {
        if (visibility) {//fade in
            fade = Fade.fadeIn(fadeComponent, 3000, fadeScale);
        } else {//fade out
            fade = Fade.fadeOut(fadeComponent, 1000, 0f);
        }//fim if-else
    }//fim fade

}//fim class
