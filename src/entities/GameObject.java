package entities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;

/**
 *
 * @author Yan Kaic
 */
public class GameObject extends Component {

  private double X;
  private double Y;
  private final JComponent object;

  public GameObject(JComponent original) {
    object = original;
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
  
  @Override
  public int getX(){
    return object.getX();
  }
  
  @Override
  public Point getLocation(){
    return object.getLocation();
  }
  
  @Override
  public Dimension getSize(){
    return object.getSize();
  }
  
  
  public void setLocation(double x, double y) {
    this.X = x;
    this.Y = y;
    object.setLocation(((int) x), ((int) y));
  }

}
