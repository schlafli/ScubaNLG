package hci;
/*
 * MapLogView.java
 *
 * Created on 12 February 2008, 13:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import javax.imageio.ImageIO;

import nlg.DiveReporter;
import nlg.Reporter;
import analytics.DiveAnalyser;
import analytics.DiveFeatures;
import analytics.DiveInterpreter;
import app.ScubaNLGMain;
/**
 * Implements functionality to provide a map visualization of geo-referenced dive log information
 * @author ssripada
 */
public class AlladinLogMapView extends Plot  implements MouseListener{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//Color settings  
  public final static Color divePointColor = new Color(255,0,0);//Red
  public final static Color diveLabelColor = new Color(0,0,255);//Blue
  
  private int divePointSize = 8; 
  public final static int ELLIPSE_SHAPE = 0;
  public final static int SQUARE_SHAPE = 1;
  private double selectedX,selectedY;
  
  //Image data
  BufferedImage img;
    
  //Data values to be plotted
  Vector<Double> xVals, yVals;
  int pointShape = ELLIPSE_SHAPE;
  ScubaNLGMain myParent;
  ArrayList<String> diveLabels;

  
  
    /** Creates a new instance of MapLogView 
     * @param diveLabels */
    public AlladinLogMapView(double x0, double y0, double x1, double y1, int x_unit, int y_unit, Vector<Double> xVal, Vector<Double> yVal,ArrayList<String> diveLabels, ScubaNLGMain parent) {
        super(x0,y0,x1,y1,x_unit,y_unit,"Scuba DiveLog Map Visualization");
        addMouseListener(this);
        this.myParent=parent;
        this.xVals = xVal;
        this.yVals = yVal;
        this.diveLabels = diveLabels;
        try {
           img = ImageIO.read(new File("world.gif"));
        } catch (IOException e) {
           System.out.println(e.toString());
        }
    }
    
    public void setPointShape(int x,int y){
      pointShape = SQUARE_SHAPE;
      selectedX = x;
      selectedY = y;
  }
    
    /**
     * Gets the x coordinate value corresponding to a real x value
     */
    public int getXGraph(double x){
        return leftMargin + (int)(((x - xMin)/(xMax - xMin)) * lengthX);
    }

    /**
     * Gets the y coordinate value corresponding to a real y value
     */
    public int getYGraph(double y){
        return topMargin+(unitYGraph*yGridLineCount) - (int)(((y - yMin)/(yMax - yMin)) * lengthY);
    }
    
    /**
     * Draws the grid lines
     */
    public void drawGrid(Graphics g, int a, int lengthX, int lengthY){
    if (a == HORIZONTAL){
      int positionY = topMargin;
      for(int k = 9;k>0;k--){
        g.setColor(gridLineColor);
        g.drawLine(leftMargin, positionY, leftMargin+lengthX, positionY);
        g.setColor(markerYColor);
        double y = k*10;
        Long V = new Long(Math.round(y));
        g.drawString(V.toString()+"N",leftMargin-35,positionY);
        positionY = positionY+unitYGraph;
      }
      g.setColor(gridLineColor);
      g.drawLine(leftMargin, positionY, leftMargin+lengthX, positionY);
      g.setColor(markerYColor);
      Long V = new Long(Math.round(0));
      g.drawString(V.toString(),leftMargin-35,positionY);
      positionY = positionY+unitYGraph;
      for(int k = 1;k<10;k++){
        g.setColor(gridLineColor);
        g.drawLine(leftMargin, positionY, leftMargin+lengthX, positionY);
        g.setColor(markerYColor);
        double y = k*10;
        V = new Long(Math.round(y));
        g.drawString(V.toString()+"S",leftMargin-35,positionY);
        positionY = positionY+unitYGraph;
      }
    }
    else{
      int positionX = leftMargin;
      for(int k = 9;k>0;k--){
        g.setColor(gridLineColor);
        g.drawLine(positionX, topMargin, positionX, topMargin + lengthY);
        g.setColor(markerXColor);
        double x =k*20;
        Long V = new Long(Math.round(x));
        g.drawString(V.toString()+"W",positionX,topMargin+lengthY+15);
        positionX = positionX+unitXGraph;
      }
      g.setColor(gridLineColor);
        g.drawLine(positionX, topMargin, positionX, topMargin + lengthY);
        g.setColor(markerXColor);
        Long V = new Long(Math.round(0));
        g.drawString(V.toString(),positionX,topMargin+lengthY+15);
        positionX = positionX+unitXGraph;
      for(int k = 1;k<10;k++){
        g.setColor(gridLineColor);
        g.drawLine(positionX, topMargin, positionX, topMargin + lengthY);
        g.setColor(markerXColor);
        double x =k*20;
        V = new Long(Math.round(x));
        g.drawString(V.toString()+"E",positionX,topMargin+lengthY+15);
        positionX = positionX+unitXGraph;
      }
    }
  }
    
    /**
     * Draws a point on the plot
     * @param string 
     */
    public void drawPoint(Graphics g, double x, double y, String string){
  g.setColor(divePointColor);
  if(pointShape == ELLIPSE_SHAPE){
    g.fillOval(getXGraph(x) - divePointSize / 2, getYGraph(y) - divePointSize / 2, divePointSize, divePointSize);
    g.setColor(diveLabelColor);
    g.drawString(string, getXGraph(x),getYGraph(y));
  }
  else{
      if((selectedX==x)&&(selectedY==y))
        g.fillRect(getXGraph(x) - divePointSize / 2, getYGraph(y) - divePointSize / 2, divePointSize, divePointSize);
      else g.fillOval(getXGraph(x) - divePointSize / 2, getYGraph(y) - divePointSize / 2, divePointSize, divePointSize);
      g.setColor(diveLabelColor);
      g.drawString(string, getXGraph(x),getYGraph(y));
  }
 
}

        
    /**
     * Responsible for all the drawing functionality
     */
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int pWidth = this.getSize().width;
    int pHeight = this.getSize().height;
    lengthX = pWidth - leftMargin - rightMargin;
    lengthY = pHeight - topMargin - bottomMargin;
    xGridLineCount = getGridLineCountOnX();
    yGridLineCount = getGridLineCountOnY();
    unitXGraph = getGridUnitXGraph(xGridLineCount);
    unitYGraph = getGridUnitYGraph(yGridLineCount);
    lengthX = xGridLineCount*unitXGraph;
    lengthY = yGridLineCount*unitYGraph;
    g2.drawImage(img,leftMargin,topMargin,lengthX,lengthY,null,null);
    drawGrid(g2, HORIZONTAL, lengthX, lengthY);
    drawGrid(g2, VERTICAL, lengthX, lengthY);
    drawLabelX(g2,"Longitude in Degrees");
    drawLabelY(g2,"Latitude","in Degrees");
    for(int i = 0; i<xVals.size();i++){
      Double x = (Double)xVals.get(i);
      Double y = (Double)yVals.get(i);
      drawPoint(g2,x.doubleValue(),y.doubleValue(),diveLabels.get(i));
    }
  }
    
  public void mousePressed(MouseEvent e) {
       saySomething("Mouse pressed; # of clicks: "
                    + e.getClickCount(), e);
    }

    public void mouseReleased(MouseEvent e) {
       saySomething("Mouse released; # of clicks: "
                    + e.getClickCount(), e);
    }

    public void mouseEntered(MouseEvent e) {
       saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) {
       saySomething("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) {
      
       double xx = (e.getX()-leftMargin)*360/lengthX;//unitX;
       double yy = (e.getY()-topMargin)*180/lengthY;//unitY;
       yy = 180 - yy;
       
       int index=0;
       for(int i = 0;i<xVals.size();i++){
           Double xval = (Double)xVals.get(i);
           Double yval = (Double)yVals.get(i);
           double left = xval.doubleValue()-divePointSize/2;
           double right = xval.doubleValue()+divePointSize/2;
           double top = yval.doubleValue()-divePointSize/2;
           double bottom = yval.doubleValue()+divePointSize/2;
                if((left<xx)&&(xx<right)&&(top<yy)&&(yy<bottom)){
                    index = i;
                    
                    setPointShape(xval.intValue(),yval.intValue());
                    repaint();
                    TreeMap<Long,Double> profile = myParent.readDiveProfile(index);
                    
                    DiveAnalyser analyser = new DiveAnalyser(myParent.getDiveNo(),profile);
                    DiveFeatures diveFeatures = analyser.extractFeatures();
                    DiveInterpreter interpreter = new DiveInterpreter(diveFeatures);
                    diveFeatures = interpreter.interpretDive();
                    Reporter reporter = new DiveReporter(diveFeatures);
                	String text = reporter.generateText();
                	myParent.displayReport(profile,text);
                }
        }
    }

    void saySomething(String eventDescription, MouseEvent e) {
        //System.out.println(eventDescription + " detected on " + e.getComponent().getClass().getName()+ ".\n");
    }


}
