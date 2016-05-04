/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.components;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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

  public Form() {
    this("A");
  }

  public Form(String name) {
    super();
    setName(name);
    init();
  }

  private void init() {
    String letter = getName().toLowerCase();
    ImageIcon icon = new ImageIcon(getClass().getResource("/letters/" + letter + "/uppercase/form.png"));
    acceptedIcon = new ImageIcon(getClass().getResource("/letters/" + letter + "/uppercase/form_yes.png"));
    recusedIcon = new ImageIcon(getClass().getResource("/letters/" + letter + "/uppercase/form_no.png"));
    setIcon(icon);
    setSize(icon.getIconWidth(), icon.getIconHeight());
    mask = new JLabel();
    mask.setSize(getSize());
    add(mask);
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
