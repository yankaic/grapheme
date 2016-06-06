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

    //lista com o caminho das letras
    private ArrayList<String> pathLetters;
    
    //objeto para leitura de arquivo de texto
    private ObjectInputStream input;
    private FileInputStream inputStream;
    
    //objeto para escrita em arquivo de texto
    private ObjectOutputStream output;
    private FileOutputStream outputStream;
      
    
    /**
     * Construtor da classe
     * Inicializa as variáveis e recupera o estado do jogo
     */
    public Controller() {
        pathLetters = new ArrayList<>();
        
        //recuperando do arquivo os caminhos das letra
        Collections.shuffle(pathLetters);//embaralhando o array 
        
    }//fim construtor


    /**
     * Método que reinicializa a lista de arquivos (letras do jogo) para a lista
     * Esse método recria a lista com todos os grafemas encontrados na pasta letters
     * 
     */
    public void reloadLetters() {
        try {
            pathLetters = new ArrayList<>();//recria a lista de files
            File directoryLetters = new File(getClass().getResource(Main.BAR + "letters").toURI());//diretório dos files
            for (int i = 0; i < directoryLetters.list().length; i++) {
                String nameLetter = directoryLetters.listFiles()[i].getName();
                pathLetters.add(nameLetter.toLowerCase());//recarregar os files
                pathLetters.add(nameLetter.toUpperCase());//recarregar os files
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
    public void loadLetters() {
        try {
            File f = new File(getClass().getResource( Main.BAR + "files" + Main.BAR + "list.graphemes").toURI());
            
            inputStream = new FileInputStream(f);//stream para a leitura
            
            //verifica se o arquivo está vazio
            if(inputStream.available()==0){
                return;
            }//fim if
            input = new ObjectInputStream(inputStream);//stream para a leitura do objeto
            pathLetters = (ArrayList<String>) input.readObject();//lê a lista atual do jogo
            Collections.shuffle(pathLetters);
        } catch (FileNotFoundException ex) {//arquivo não encontrado
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {//erro na leitura
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }       
        finally {//fechando os streams
            try {
                if(input!=null)
                    input.close();
                if(inputStream!=null)
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
    public String getLetter(){
       return (pathLetters.isEmpty()) ? null : pathLetters.remove(0);
    }//fim getLetter
    
    public String[] getLasts(int size){
      String list[] = new String[size];
      for (int i = 0; i < size; i++) {
        try {
          list[i] = pathLetters.get(i);
        }
        catch (ArrayIndexOutOfBoundsException e) {
          list[i] = null;
        }
      }
      return list;
    }
    
    /**
     * Método que verifica se o array de letras está vazio. Isso pode indicar que 
     * o jogo acabou, ou que é a primeira vez
     * @return 
     */
    public boolean isLettersEmpty(){
        return pathLetters.isEmpty();
    }//fim getLetters
    
    /**
     * Método que salva um lista de files em um arquivo
     */
    public void saveFiles() {
        try {
            outputStream = new FileOutputStream(new File(getClass().getResource(Main.BAR + "files" + Main.BAR + "list.graphemes").toURI()));//stream para o arquivo
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
