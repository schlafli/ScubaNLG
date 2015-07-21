package hci;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


/**
 * Implements generic functionality for drawing plots.
 */
public class Plot extends JPanel{
  
	private static final long serialVersionUID = 1L;
public final static int VERTICAL = 0, HORIZONTAL = 1;
  //Color settings
  public final static Color gridLineColor = Color.LIGHT_GRAY;//new Color(0,255,0);//Green
  public final static Color markerXColor = new Color(0,0,255);//Blue
  public final static Color markerYColor = new Color(0,0,255);//Blue
   
  
  public int leftMargin, rightMargin, topMargin, bottomMargin;
  
  public double    xMin,   //minimum value on X axis
            xMax,   //maximum value on X axis
            yMin,   //minimum value on Y axis
            yMax;   //maximum value on Y axis
  public int   unitXReal,  //one unit on X variable
        unitYReal;  //one unit on Y variable
  public int   unitXGraph, //one unit on X axis in the graph
        unitYGraph; //one unit on Y axis in the graph
  public int   lengthX,    //length of X axis
        lengthY;    //length of Y axis
  public int xGridLineCount,     //number of tick marks on the X axis
      yGridLineCount;     //number of tick marks on the Y axis
  
    
    /**
     * Creates the scale and margins for the graph. Also sets the window parameters.
     */
  public Plot(double x0, double y0, double x1, double y1,int x_unit, int y_unit,String tooltip){
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.black));
    setToolTipText(tooltip);
    setScale(x0, x1, y0, y1);
    setMargins(150, 75, 75, 75);
    unitXReal=x_unit;
    unitYReal=y_unit;
  }

    /**
     * Computes the number of grid lines for X axis
     */
  public int getGridLineCountOnX(){
     Double tmp = new Double((xMax-xMin)/unitXReal);
     return tmp.intValue();
  }
  
    /**
     * Computes the number of grid lines for the Y axis
     */
  public int getGridLineCountOnY(){
     Double tmp = new Double((yMax-yMin)/unitYReal);
     return tmp.intValue();
  }
  
    /**
     * Computes the size of grid unit on the graph for the X axis
     */
  public int getGridUnitXGraph(int num){
    Double temp = new Double((lengthX)/(num));
    return temp.intValue();
  }
  
    /**
     * Computes the size of grid unit on the graph for the Y axis
     */
  public int getGridUnitYGraph(int num){
    Double temp = new Double((lengthY)/(num));
    return temp.intValue();
  }
  
    /**
     * Draws labels for the X axis
     */
  public void drawLabelX(Graphics g, String label){
      int positionX;
      Double tmp = new Double(lengthX/2);
      positionX = leftMargin+tmp.intValue();
      g.drawString(label,positionX,topMargin+lengthY+50);
  }
  
    /**
     * Draws the labels for Y axis
     */
  public void drawLabelY(Graphics g, String label1,String label2){
      int positionY;
      Double tmp = new Double(lengthY/2);
      positionY = topMargin+tmp.intValue();
      g.drawString(label1,leftMargin-125,positionY);
      g.drawString(label2,leftMargin-125,positionY+25);
  }

    /**
     * Sets the scale for the plot
     */
  public void setScale(double x0, double x1, double y0, double y1){
    xMin = x0; xMax = x1;
    yMin = y0; yMax = y1;
}

    /**
     * Sets the margins for the plot
     */
  public void setMargins(int l, int r, int b, int t){
    leftMargin = l; rightMargin = r;
    bottomMargin = b; topMargin = t;
}

  

  
}
