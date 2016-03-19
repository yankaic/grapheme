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
  private double WIDTH;
  private double HEIGHT;
  private final JComponent object;

  public GameObject(JComponent original) {
    object = original;
    Point location = getLocation();
    this.X = location.getX();
    this.Y = location.getY();
    this.WIDTH = original.getSize().width;
    this.HEIGHT = original.getSize().height;
  }//fim Construtor

  public double getXF() {
    return X;
  }

  public double getYF() {
    return Y;
  }
  
  public double getWidthF(){
      return WIDTH;
  }
  
  public double getHeightF(){
      return HEIGHT;
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
  
  public void setSize(double w, double h){
      this.WIDTH=w;
      this.HEIGHT=h;
      object.setSize((int)w, (int)h);
  }

}
