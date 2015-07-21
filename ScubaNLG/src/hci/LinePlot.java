package hci;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
/**
 *This class implements the functionality for drawing the dive profile.
 * @author  ssripada
 */

@SuppressWarnings("serial")
public class LinePlot extends JPanel{
  public final static int VERTICAL = 0, HORIZONTAL = 1;
  //Color settings
  public final static Color gridLineColor = Color.LIGHT_GRAY;//new Color(0,255,0);//Green
  public final static Color markerXColor = new Color(0,0,255);//Blue
  public final static Color markerYColor = new Color(0,0,255);//Blue
  public final static Color firstSeriesColor = new Color(0,0,255);//Blue
  public final static Color secondSeriesColor = new Color(255,0,0);//Red
  
  private int pointSize = 6, leftMargin, rightMargin, topMargin, bottomMargin;
  
  private double xMin, xMax, yMin, yMax;
  private int unitX, unitY;
  int lengthX, lengthY;
  double maxDepth = 0;
 
  TreeMap<Long,Double> originalSeries;
  long baseTime;
  
  public LinePlot(double x0,double y0,double x1,double y1,TreeMap<Long,Double> series1,long initTime,double mDepth) {
    setBackground(Color.white);
    setBorder(BorderFactory.createLineBorder(Color.black));
    setToolTipText("Scuba Dive Report");
    setScale(x0, x1, y0, y1);
    setMargins(150, 75, 75, 75);
    this.originalSeries = series1;
    baseTime = initTime;
    maxDepth = mDepth;
  }

  public int getGridLineCountOnX(){
     Double tmp = new Double((xMax-xMin)/60);
     return tmp.intValue();
  }
  
  public int getGridLineCountOnY(){
     Double tmp = new Double((yMax-yMin)/5);
     return tmp.intValue();
  }
  
  public int getGridUnitX(int num){
    Double temp = new Double((lengthX)/(num));
    return temp.intValue();
  }
  
  public int getGridUnitY(int num){
    Double temp = new Double((lengthY)/(num));
    return temp.intValue();
  }
  
  public void drawAxis(Graphics2D g, int a,int lengthX, int lengthY){
    if (a == HORIZONTAL){
      g.drawLine(leftMargin,topMargin,leftMargin+lengthX,topMargin);
    }
    else{
      g.drawLine(leftMargin,topMargin,leftMargin,topMargin+lengthY);
    }
  }

  public void drawGrid(Graphics g, int a, int lengthX, int lengthY){
    if (a == HORIZONTAL){
      int positionY = topMargin+unitY;
      int k = 0;
      while(positionY<=topMargin+lengthY){
        g.setColor(gridLineColor);
        g.drawLine(leftMargin, positionY, leftMargin+lengthX, positionY);
        g.setColor(markerYColor);
        double y = 5+k*5;
        Long V = new Long(Math.round(y));
        g.drawString(V.toString()+"m",leftMargin-35,positionY);
        positionY = positionY+unitY;
        k++;
      }
    }
    else{
      int positionX = leftMargin+unitX;
      int k = 0;
      while(positionX<=leftMargin+lengthX){
        g.setColor(gridLineColor);
        g.drawLine(positionX, topMargin, positionX, topMargin + lengthY);
        g.setColor(markerXColor);
        if(((k+1)%2)==0){
            double x =1+k;
            Long V = new Long(Math.round(x));
            g.drawString(V.toString()+"'",positionX,topMargin+lengthY+15);
        }
        positionX = positionX+unitX;
        k++;
      }
    }
  }
  
  public void drawLabelX(Graphics g, String label){
      int positionX;
      Double tmp = new Double(lengthX/2);
      positionX = leftMargin+tmp.intValue();
      g.drawString(label,positionX,topMargin+lengthY+50);
  }
  
  public void drawLabelY(Graphics g, String label1,String label2){
      int positionY;
      Double tmp = new Double(lengthY/2);
      positionY = topMargin+tmp.intValue();
      g.drawString(label1,leftMargin-125,positionY);
      g.drawString(label2,leftMargin-125,positionY+25);
  }
 
  public void setScale(double x0, double x1, double y0, double y1){
    xMin = x0; xMax = x1;
    yMin = y0; yMax = y1;
  }

  public void setMargins(int l, int r, int b, int t){
    leftMargin = l; rightMargin = r;
    bottomMargin = b; topMargin = t;
  }

  public int getXGraph(double x){
    return leftMargin + (int)(((x - xMin)/(xMax - xMin)) * (getSize().width - leftMargin - rightMargin));
  }

  public int getYGraph(double y){
    return topMargin + (int)(((y - yMin)/(yMax - yMin)) * (getSize().height - bottomMargin - topMargin));
  }

  public void drawPoint(Graphics g, double x, double y){
          g.fillOval(getXGraph(x) - pointSize / 2, getYGraph(y) - pointSize / 2, pointSize, pointSize);
  }
  
  public void drawSeries(Graphics g, TreeMap<Long,Double> series,Color col){
	  //TreeSet<YSegment> segList = series.getSegmentList();
	  Long iTime = null;
      Double iValue = null;
      for (Iterator<Map.Entry<Long,Double>>  i = series.entrySet().iterator(); i.hasNext(); ) {
        Map.Entry<Long,Double> e = (Map.Entry<Long,Double>) i.next();
        Long fTime = (Long) e.getKey();
        Double fValue = (Double) e.getValue();
        drawPoint(g, (fTime.longValue() - baseTime),
                  fValue.doubleValue());
        if((iTime!=null)&&(iValue!=null)){
        g.setColor(col);
        g.drawLine(getXGraph( (iTime.longValue()  - baseTime)),
                    getYGraph(iValue.doubleValue()),
                    getXGraph( (fTime.longValue() - baseTime)),
                    getYGraph(fValue.doubleValue()));
        }
        iTime = fTime;
        iValue = fValue;
      }
  }
  
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    int pWidth = this.getSize().width;
    int pHeight = this.getSize().height;
    lengthX = pWidth - leftMargin - rightMargin;
    lengthY = pHeight - topMargin - bottomMargin;
    int xNum = getGridLineCountOnX();
    int yNum = getGridLineCountOnY();
    unitX= getGridUnitX(xNum);
    unitY= getGridUnitY(yNum);
    drawAxis(g2, HORIZONTAL, unitX*xNum, unitY*yNum);
    drawAxis(g2, VERTICAL, unitX*xNum, unitY*yNum);
    drawGrid(g2, HORIZONTAL, unitX*xNum, unitY*yNum);
    drawGrid(g2, VERTICAL, unitX*xNum, unitY*yNum);
    drawLabelX(g2,"Time in Minutes");
    drawLabelY(g2,"Depth","in Meters");
    g2.setColor(new Color(0, 0, 0));
    if(originalSeries!=null)
        drawSeries(g2,originalSeries,firstSeriesColor);
  }
}
