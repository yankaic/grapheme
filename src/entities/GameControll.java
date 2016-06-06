/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Collections;
import view.SwipeView;
import view.components.Form;

/**
 *
 * @author shanks
 */
public class GameControll {

  private SwipeView view;//janela da aplicação
  private Controller controllerLetters;//objeto que controla o estado do jogo

  public GameControll() {
    controllerLetters = new Controller();  
    view = new SwipeView();
  }//fim contrutor

  public void initGame() {
    //carregando ou recarregando a lista de letras do jogo
    controllerLetters.loadLetters();//carrega as letras

    if (controllerLetters.isLettersEmpty()) {
      controllerLetters.reloadLetters();
    }//fim if  
    //inicilizando a janela
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        view = new SwipeView();
        view.setVisible(true);
      }//fim run
    });
  }//fim initGame

  /**
   * Método que retorna a próxima letra do jogo
   * @return letter Letter
   */
  public Letter nextLetter() {
    Letter letter = new Letter(controllerLetters.getLetter());
    view.addLetter(letter);
    return letter;
  }//fim nextLetter

  public Form[] nextForms(int size) {
    Form forms[] = new Form[size];
    String names[] = controllerLetters.getLasts(size);
    for (int i = 0; i < size; i++) {
      forms[i] = new Form(new Letter(names[i]));
    }
    return forms;
  }
}
