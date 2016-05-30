package entities;

import graphemes.Main;
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
 * Classe que controla a dinâmica do jogo. Atribuições - Cria, lê e salva a
 * lista de caminhos das letras do jogo - Mantém salvo o estágio do jogo
 *
 * @author shanks
 */
public class Controller extends ArrayList<File> {

  //objeto para leitura de arquivo de texto
  private ObjectInputStream input;
  private FileInputStream inputStream;

  //objeto para escrita em arquivo de texto
  private ObjectOutputStream output;
  private FileOutputStream outputStream;

  /**
   * Construtor da classe Inicializa as variáveis e recupera o estado do jogo
   */
  public Controller() {
    init();
  }//fim construtor

  private void init() {
    try {
      load();   //tentando carregar uma lista salva
    }
    catch (IOException | ClassNotFoundException ex) {
      reload(); // se a lista carregada estiver vazia
    }
  }

  /**
   * Método que reinicializa a lista de arquivos (letras do jogo) para a lista
   * Esse método recria a lista com todos os grafemas encontrados na pasta
   * letters
   *
   */
  public void reload() {
    try {
      clear();
      File directoryLetters = new File(getClass().getResource(Main.BAR + "letters").toURI());//diretório dos files
      for (int i = 0; i < directoryLetters.list().length; i++) {
        add(directoryLetters.listFiles()[i]);//recarregar os files
      }//fim for      
    }
    catch (URISyntaxException ex) {
      Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }//fim try-catch   
  }//fim restartListOfFiles

  /**
   * Método que lê o arquivo (save) do jogo e cria uma lista contendo os paths
   * salvos nesse arquivo
   *
   * @throws java.io.IOException
   * @throws java.lang.ClassNotFoundException
   */
  public void load() throws IOException, ClassNotFoundException {
    try {
      File f = new File(getClass().getResource(Main.BAR + "files" + Main.BAR + "list.graphemes").toURI());
      inputStream = new FileInputStream(f);//stream para a leitura
      input = new ObjectInputStream(inputStream);//stream para a leitura do objeto
      ArrayList<File> list = (ArrayList<File>) input.readObject();//lê a lista atual do jogo
      Collections.copy(this, list);
      if (isEmpty()) {
        throw new IOException("Empty list");
      }
    } //fim loadFiles
    catch (URISyntaxException ex) {
      Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * Método que retorna um caminho para letra do jogo
   *
   * @return file File: caminho raiz de uma letra randômica null : se a lista
   * estiver vazia
   */
  public File getLetter() {
    return (isEmpty()) ? null : remove(0);
  }//fim getLetter

  /**
   * Método que salva um lista de files em um arquivo
   */
  public void save() {
    try {
      outputStream = new FileOutputStream(new File(getClass().getResource(Main.BAR + "files" + Main.BAR + "list.graphemes").toURI()));//stream para o arquivo
      output = new ObjectOutputStream(outputStream);//writter para o objeto
      output.writeObject(this);//escreve a lista de files no arquivo
    }
    catch (FileNotFoundException ex) {//arquivo não encontrado
      Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (URISyntaxException | IOException ex) {//caminho errado
      Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
    }
    finally {//fecha os streams
      try {
        output.close();
        outputStream.close();
      }
      catch (IOException ex) {
        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
      }//fim try-catch
    }//fim try-catch-finally
  }//fim saveFiles

}//fim class
