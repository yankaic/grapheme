/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import effects.Translation;
import entities.GameObject;
import java.awt.Point;
import view.components.Letter;

/**
 *
 * @author Yan Kaic
 */
public class SwipeView extends javax.swing.JFrame {

  /**
   * Creates new form SwipeView
   */
  public SwipeView() {
    initComponents();
    setLocationRelativeTo(null);
    init();
  }
  
  private void init(){
      Letter letter = new Letter();
      letter.setLocation(900, 10);
      letterPanel.add(letter);
      Translation t = new Translation(new GameObject(letter), new Point(450, 10), 1400);
      t.start();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        workPanel = new javax.swing.JPanel();
        tvLabel = new javax.swing.JLabel();
        letterPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(900, 600));
        setMinimumSize(new java.awt.Dimension(900, 600));
        setResizable(false);
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        workPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 20, 25, 0));
        workPanel.setOpaque(false);
        workPanel.setLayout(new java.awt.BorderLayout());

        tvLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.png"))); // NOI18N
        workPanel.add(tvLabel, java.awt.BorderLayout.CENTER);

        letterPanel.setBackground(new java.awt.Color(234, 227, 212));
        letterPanel.setOpaque(false);
        letterPanel.setPreferredSize(new java.awt.Dimension(860, 130));

        javax.swing.GroupLayout letterPanelLayout = new javax.swing.GroupLayout(letterPanel);
        letterPanel.setLayout(letterPanelLayout);
        letterPanelLayout.setHorizontalGroup(
            letterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        letterPanelLayout.setVerticalGroup(
            letterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        workPanel.add(letterPanel, java.awt.BorderLayout.PAGE_END);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/tv.png"))); // NOI18N
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        workPanel.add(jLabel2, java.awt.BorderLayout.LINE_END);

        getContentPane().add(workPanel);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N
        jPanel2.add(jLabel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
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
      }
    });
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel letterPanel;
    private javax.swing.JLabel tvLabel;
    private javax.swing.JPanel workPanel;
    // End of variables declaration//GEN-END:variables
}
