package analytics;
import java.util.*;

/** This class defines a number of static utlity functions
 * @author ssripada
 */
public class Utils
{


    /** Computes the max value of a Collection of double values
     * @param buffer Collection of doubles
     * @return max value
     */

    static public double myMax(Collection<Double> buffer){
        Vector<Double> list = new Vector<Double>(buffer);
        double rtn = ((Double)list.elementAt(0)).doubleValue();
        for(int i =0; i<buffer.size();i++){
            Double x = (Double)list.elementAt(i);
            if(x.doubleValue()>rtn)
                rtn = x.doubleValue();
        }
        return rtn;
    }
    
    /** Computes the min value of a Collection of double values
     * @param buffer Collection of doubles
     * @return min value
     */

    static public double myMin(Collection<Double> buffer){
    	Vector<Double> list = new Vector<Double>(buffer);
        double rtn = ((Double)list.elementAt(0)).doubleValue();
        for(int i =0; i<buffer.size();i++){
            Double x = (Double)list.elementAt(i);
            if(x.doubleValue()<rtn)
                rtn = x.doubleValue();
        }
        return rtn;
    }
    
    /** Computes the longitude value from a string
     * @param str input string
     * @return the longitude value
     */
    
    public static double convertLongitude(String str){
        double rtn = 0;
        if(str!=null){
            Double num = new Double(str.substring(0,str.length()-1));

            if(str.charAt(str.length()-1)=='E'){
             rtn = 180 + num.doubleValue();
            }
            else if(str.charAt(str.length()-1)=='W'){
            rtn = 180 - num.doubleValue();
            }
        }

        return rtn;
      }
    
    /** Computes the latitude value from a string
     * @param str input string
     * @return latitude value
     */

public static double convertLatitude(String str){
        double rtn = 0;
        if(str!=null){
            Double num = new Double(str.substring(0,str.length()-1));
            if(str.charAt(str.length()-1)=='N'){
            rtn = 90 + num.doubleValue();
            }
            else if(str.charAt(str.length()-1)=='S'){
            rtn = 90 - num.doubleValue();
            }
        }
        return rtn;
      }

/** Computes seconds from a string
 * @param timeString input string
 * @return seconds
 */

public static Long getTimeInSeconds(String timeString){
    StringTokenizer st = new StringTokenizer(timeString,"'\"");
    return new Long((((new Long(st.nextToken())).longValue())*60)+(new Long(st.nextToken())).longValue());
  }
       
}
