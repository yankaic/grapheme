/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import entities.Letter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Yan Kaic
 */
public class Form extends JLabel {

  private JLabel mask;
  private ImageIcon acceptedIcon;
  private ImageIcon recusedIcon;
  private Letter letter;
  private static final int ICON_WIDTH = 202;

  public Form() {
    this.letter = new Letter("A");
    setName(letter.getName());
    init();
  }

  public Form(Letter letter) {
    super();
    this.letter = letter;
    setName(letter.getName());
    init();
    setVisible(false);
  }

  private void init() {
    try {
      String path = (letter.isLowerCaseLetter()) ? letter.getLowerCasePath()
              : letter.getUpperCasePath();
      ImageIcon icon = new ImageIcon(new URL(path + "form.png"));
      acceptedIcon = new ImageIcon(new URL(path + "form_yes.png"));
      recusedIcon = new ImageIcon(new URL(path + "form_no.png"));
      setIcon(icon);
      setSize(ICON_WIDTH, icon.getIconHeight());
//      setPreferredSize(getSize());
      setHorizontalAlignment(CENTER);
      setLayout(new BorderLayout());
      mask = new JLabel();
      mask.setSize(getSize());
      mask.setHorizontalAlignment(CENTER);
      add(mask);
    }
    catch (MalformedURLException ex) {
      Logger.getLogger(Form.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public void recuse() {
    mask.setIcon(recusedIcon);
  }

  public void accept() {
    mask.setIcon(acceptedIcon);
  }

  public void normalize() {
    mask.setIcon(null);
  }

  public void fit(Letter letter) throws IllegalArgumentException {
    if (!check(letter)) {
      throw new IllegalArgumentException("this letter is not compatible.");
    }
    letter.setLocation(0, 0);
    add(letter, 0);
  }

  public boolean check(Letter letter) {
    if (intersection(letter) > 0) {
      if (isCompatible(letter)) {
        accept();
        return true;
      }
      recuse();
      return false;
    }
    normalize();
    return false;
  }

  public boolean isCompatible(Letter letter) {
    return getName().equals(letter.getName());
  }

  public int intersection(Letter letter) {
    Rectangle area = getBounds();
    area.setLocation(getLocationOnScreen());

    Rectangle letterArea = letter.getBounds();
    letterArea.setLocation(letter.getLocationOnScreen());

    Rectangle intersection = area.intersection(letterArea);
    int signal = intersection.width < 0 && intersection.height < 0 ? -1 : 1;
    return intersection.width * intersection.height * signal;
  }

}
