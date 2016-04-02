package entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que controla a dinâmica do jogo.
 *  Atribuições
 * - Cria, lê e salva a lista de caminhos das letras do jogo
 * - Mantém salvo o estágio do jogo
 * @author shanks
 */
public class Controller {

    public static ArrayList<File> pathLetters;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private FileInputStream inputStream;
    private FileOutputStream outputStream;

    public Controller() {
        pathLetters = new ArrayList<>();
        //restartListOfFiles();
        reloadFiles();
        Collections.shuffle(pathLetters);
    }//fim construtor

    private void initView() {

    }//fim initView

    /**
     * Método que reinicializa a lista de arquivos (letras do jogo) para a lista
     * Esse método recria a lista com todos os grafemas encontrados na pasta letters
     */
    public void restartListOfFiles() {
        try {
            pathLetters = new ArrayList<>();//recria a lista de files
            File directoryLetters = new File(getClass().getResource(File.separator + "letters").toURI());//diretório dos files
            for (int i = 0; i < directoryLetters.list().length; i++) {
                pathLetters.add(directoryLetters.listFiles()[i]);//recarregar os files
            }//fim for      
            Collections.shuffle(pathLetters);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }//fim try-catch   
        saveFiles();//salva a nova lista no arquivo
    }//fim restartListOfFiles

    /**
     * Método que lê o arquivo (save) do jogo e cria uma lista contendo os paths salvos nesse arquivo
     */
    @SuppressWarnings("unchecked")
    private void reloadFiles() {
        try {
            File f = new File(getClass().getResource(File.separator + "files" + File.separator + "list.graphemes").toURI());
            inputStream = new FileInputStream(f);//stream para a leitura
            input = new ObjectInputStream(inputStream);//stream para a leitura do objeto
            pathLetters = (ArrayList<File>) input.readObject();//lê a lista atual do jogo
            Collections.shuffle(pathLetters);
        } catch (URISyntaxException | ClassNotFoundException ex) {//caminho não encontrado
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {//arquivo não encontrado
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {//erro na leitura
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {//fechando os streams
            try {
                input.close();
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }//fim try-catch
        }//fim try-catch-finally
    }//fim loadFiles

    /**
     * Método que retorna um caminho para letra do jogo
     * @return file File: caminho raiz de uma letra randômica
     *         null : se a lista estiver vazia
     */
    public File getLetter(){
       return (pathLetters.isEmpty()) ? null : pathLetters.remove(0);
    }//fim getLetter
    
    /**
     * Método que salva um lista de files em um arquivo
     */
    public void saveFiles() {
        try {
            outputStream = new FileOutputStream(new File(getClass().getResource(File.separator + "files" + File.separator + "list.graphemes").toURI()));//stream para o arquivo
            output = new ObjectOutputStream(outputStream);//writter para o objeto
            output.writeObject(pathLetters);//escreve a lista de files no arquivo
        } catch (FileNotFoundException ex) {//arquivo não encontrado
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException | IOException ex) {//caminho errado
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {//fecha os streams
            try {
                output.close();
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }//fim try-catch
        }//fim try-catch-finally
    }//fim saveFiles
   
}//fim class
