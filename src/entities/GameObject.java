package entities;

import java.awt.Component;
import java.awt.Point;
import javax.swing.JComponent;

/**
 *
 * @author Yan Kaic
 */
public class GameObject extends JComponent {

  private double X;
  private double Y;

  public GameObject() {
    super();
    Point location = getLocation();
    this.X = location.getX();
    this.Y = location.getY();
  }

  public double getXF() {
    return X;
  }

  public double getYF() {
    return Y;
  }

  public void setLocation(double x, double y) {
    this.X = x;
    this.Y = y;
    setLocation(((int) x), ((int) y));
  }

}
