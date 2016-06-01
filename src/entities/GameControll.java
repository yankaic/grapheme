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
  }//fim contrutor

  public void initGame() {
    //carregando ou recarregando a lista de letras do jogo
    controllerLetters.loadLetters();//carrega as letras

    if (controllerLetters.isLettersEmpty()) {
      controllerLetters.reloadLetters();
    }//fim if

    //inicilizando a janela
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(SwipeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(SwipeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(SwipeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(SwipeView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new SwipeView().setVisible(true);
      }//fim run
    });
  }//fim initGame

  public Letter nextLetter() {

    Letter letter = new Letter(controllerLetters.getLetter());

    return letter;
  }//fim nextLetter

  public Form[] nextForms(int size) {
    Form forms[] = new Form[size];
    String names[] = controllerLetters.getLasts(size);
    for (int i = 0; i < size; i++) {
      forms[i] = new Form(names[i]);
    }
    return forms;
  }
}
