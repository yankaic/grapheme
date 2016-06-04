package view;

import effects.Translation;
import entities.GameLabel;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import view.components.Form;
import entities.Letter;
import graphemes.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Yan Kaic
 */
public class SwipeView extends javax.swing.JFrame {
  private Letter letter;
  /**
   * Creates new form SwipeView
   */
  public SwipeView() {
    initComponents();
    setLocationRelativeTo(null);
    setIconImage(new ImageIcon(getClass().getResource("/icons/fab.png")).getImage());
    letter = Main.gameControll.nextLetter();
    addLetter(letter);
  }

  /**
   * Método que adiciona uma nova letra na janela
   * Ao mesmo tempo que adiciona todos os eventos pertinente as letras
   * @param letter Letter : nova letra sendo adicionada na janela
   */
  private void addLetter(Letter letter) {
    letter.setLocation(900, 10);
    letterPanel.add(letter);
    initForms();
    
    Translation t = new Translation(letter, new Point(10, 10), 1400);
    t.start();
    letterEvents(letter);
    repaint();
  }//fim addLetter

  private void letterEvents(Letter letter) {
    letter.addMouseListener(new MouseAdapter() {

      @Override
      public void mousePressed(MouseEvent me) {
        Point location = letterPanel.getLocation();
        location.x += letter.getX();
        location.y += letter.getY();
        letter.setLocation(location);
        letter.setOldLocation(location);
        letterPanel.remove(letter);
        topPanel.add(letter);
        topPanel.repaint();
      }

      @Override
      public void mouseReleased(MouseEvent mr) {
        try {
          Form selectedForm = greaterIntersection(letter);
          selectedForm.fit(letter);
          letter.setOldLocation(letter.getLocation());
          Timer animation  = new Timer(500, (ActionEvent e) -> {
              if(letter.isLowerCaseLetter()){//animação de letra minúscula
                  lowerCaseAnimation.setLetter(letter);
                  transitionTopPanel.setVisible(true);
                  lowerCaseAnimation.setVisible(true);                          
              }else{//animação de letra maiúscula
                  
              }//fim if-else
          });//fim timer
          animation.setRepeats(false);//desabilita a repetição
          animation.start();
        }
        catch (IllegalArgumentException uae) {
          final int delayTime = 300;
          Translation animation = new Translation(letter, letter.getOldLocation(), delayTime);
          animation.start();

          Timer timer = new Timer(delayTime + 100, ActionEvent -> {
            Point location = letter.getLocation();
            location.x = 10;
            location.y = 10;
            letter.setLocation(location);

            topPanel.remove(letter);
            letterPanel.add(letter);
            checkAll(letter);
            System.out.println("soltou");
            System.out.println(letter.getLocation());
          });
          timer.setRepeats(false);
          timer.start();
        }
      }
    });

    letter.addMouseMotionListener(new MouseMotionAdapter() {
      @Override
      public void mouseDragged(MouseEvent me) {
        Form form = greaterIntersection(letter);
        form.check(letter);
      }
    });
  }

  public void checkAll(Letter letter) {
    for (Component c : itensTable.getComponents()) {
      Form form = (Form) c;
      form.check(letter);
    }
  }

  private Form greaterIntersection(Letter letter) {
    int max = -999999;
    Form greater = null;
    for (Component c : itensTable.getComponents()) {
      Form form = (Form) c;
      form.normalize();

      if (form.intersection(letter) > max) {
        max = form.intersection(letter);
        greater = form;
      }
    }
    return greater;
  }

  /**
   * Método que adiciona uma label no painel transition, responsável pela animação 
   *    de transição dos exemplos das letras
   * @param label JLabel
   */  
  public static void addTransitionLabels(JLabel label, int index){
      lowerCaseAnimation.add(label,index);
  }//fim addLabels
  
  public LowerCaseLetterAnimation getLowerCaseAnimetionPanel(){
      return lowerCaseAnimation;
  }
 

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    fadeBackgroudLabel = new GameLabel();
    topPanel = new javax.swing.JPanel();
    lowerCaseAnimation = new view.LowerCaseLetterAnimation();
    transitionTopPanel = new javax.swing.JPanel();
    workPanel = new javax.swing.JPanel();
    letterPanel = new javax.swing.JPanel();
    jPanel2 = new javax.swing.JPanel();
    tV1 = new view.components.TV();
    tablePanel = new javax.swing.JPanel();
    itensTable = new javax.swing.JPanel();
    backTable = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    backPanel = new javax.swing.JPanel();
    backLabel = new javax.swing.JLabel();

    fadeBackgroudLabel.setBackground(new java.awt.Color(254, 1, 5));
    fadeBackgroudLabel.setMaximumSize(new java.awt.Dimension(900, 600));
    fadeBackgroudLabel.setMinimumSize(new java.awt.Dimension(900, 600));
    fadeBackgroudLabel.setVisible(false);
    //fadeBackgroudLabel.setVisible(false);
    fadeBackgroudLabel.setPreferredSize(new java.awt.Dimension(900, 600));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Grafemas");
    setMinimumSize(new java.awt.Dimension(900, 600));
    setResizable(false);
    getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

    topPanel.setOpaque(false);
    topPanel.setRequestFocusEnabled(false);

    lowerCaseAnimation.setVisible(false);

    javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
    topPanel.setLayout(topPanelLayout);
    topPanelLayout.setHorizontalGroup(
      topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 900, Short.MAX_VALUE)
      .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(topPanelLayout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(lowerCaseAnimation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(0, 0, Short.MAX_VALUE)))
    );
    topPanelLayout.setVerticalGroup(
      topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 600, Short.MAX_VALUE)
      .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(topPanelLayout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(lowerCaseAnimation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGap(0, 0, Short.MAX_VALUE)))
    );

    getContentPane().add(topPanel);

    transitionTopPanel.setOpaque(false);
    transitionTopPanel.setLayout(null);
    transitionTopPanel.setVisible(false);
    getContentPane().add(transitionTopPanel);

    workPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 20, 25, 0));
    workPanel.setOpaque(false);
    workPanel.setLayout(new java.awt.BorderLayout());

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

    jPanel2.setOpaque(false);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(tV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(tV1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(144, Short.MAX_VALUE))
    );

    workPanel.add(jPanel2, java.awt.BorderLayout.LINE_END);

    tablePanel.setMaximumSize(new java.awt.Dimension(608, 395));
    tablePanel.setMinimumSize(new java.awt.Dimension(608, 395));
    tablePanel.setOpaque(false);
    tablePanel.setPreferredSize(new java.awt.Dimension(608, 395));
    tablePanel.setLayout(new javax.swing.OverlayLayout(tablePanel));

    itensTable.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 55, 1, 95));
    itensTable.setMaximumSize(new java.awt.Dimension(608, 395));
    itensTable.setMinimumSize(new java.awt.Dimension(608, 395));
    itensTable.setOpaque(false);
    itensTable.setPreferredSize(new java.awt.Dimension(608, 395));
    itensTable.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 30));
    tablePanel.add(itensTable);

    backTable.setOpaque(false);
    backTable.setLayout(new java.awt.BorderLayout());

    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/table.png"))); // NOI18N
    backTable.add(jLabel1, java.awt.BorderLayout.CENTER);

    tablePanel.add(backTable);

    workPanel.add(tablePanel, java.awt.BorderLayout.CENTER);

    getContentPane().add(workPanel);

    backPanel.setBackground(new java.awt.Color(0, 0, 0));
    backPanel.setOpaque(false);
    backPanel.setLayout(new java.awt.BorderLayout());

    backLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/background.png"))); // NOI18N
    backPanel.add(backLabel, java.awt.BorderLayout.CENTER);

    getContentPane().add(backPanel);

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
  private javax.swing.JLabel backLabel;
  private javax.swing.JPanel backPanel;
  private javax.swing.JPanel backTable;
  private static javax.swing.JLabel fadeBackgroudLabel;
  private javax.swing.JPanel itensTable;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel letterPanel;
  private static view.LowerCaseLetterAnimation lowerCaseAnimation;
  private view.components.TV tV1;
  private javax.swing.JPanel tablePanel;
  private javax.swing.JPanel topPanel;
  private static javax.swing.JPanel transitionTopPanel;
  private javax.swing.JPanel workPanel;
  // End of variables declaration//GEN-END:variables

  private void initForms() {
    final int formsSize = 4;
    Letter current = (Letter) letterPanel.getComponent(0);
    ArrayList<Form> forms = new ArrayList<>();
    forms.add(new Form(current.getName()));
    Form received[] = Main.gameControll.nextForms(formsSize);
    forms.addAll(Arrays.asList(received));
    Collections.shuffle(forms);
    
    for (Form form : forms) {
      itensTable.add(form);
    }
   
  }
}
