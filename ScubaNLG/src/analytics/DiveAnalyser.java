package analytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 
 */

/**
 * This class has all the functionality to split a dive into its component divelets, extract these divelets and finally compute a number of features that represent the dive for interpretation and reporting. A lot of functionality could be added here to create richer content for the NLG reports.
 * @author csc242
 *
 */
public class DiveAnalyser {

	private DiveFeatures diveFeatures = new DiveFeatures();

	public DiveAnalyser(Double diveNo, TreeMap<Long, Double> diveProfile) {
		this.diveFeatures.setDiveNo(diveNo);
		this.diveFeatures.setDiveProfile(diveProfile);
	}

	public DiveFeatures extractFeatures() {
		//
		DiveletFeatures firstDiveletFeatures = new DiveletFeatures();
		DiveletFeatures secondDiveletFeatures = new DiveletFeatures();
		List<Long> diveletLimitPoints = new ArrayList<Long>();
		diveFeatures.setDiveDepth(Utils.myMax(diveFeatures.getDiveProfile().values()));
		//System.out.println("Depth of the dive: "+diveFeatures.getDiveDepth());
		if(diveFeatures.getDiveDepth()> PADITable.safeDepthMinimumInMeters)
			diveFeatures.setRealDive(true);
		if(diveFeatures.isRealDive()){
			
			diveletLimitPoints=computeLimitsOfDivelets(diveletLimitPoints);
			//System.out.println("first: "+diveletLimitPoints);
		    if(diveletLimitPoints.size()>2)
		    	diveFeatures.setSurfaceIntervalTime((diveletLimitPoints.get(2)-diveletLimitPoints.get(1))/60);
		    //System.out.println("SI: "+diveFeatures.getSurfaceIntervalTime());
		    diveletLimitPoints=refineLimitsOfDivelets(diveletLimitPoints);
			if(diveletLimitPoints.size()==2){
				diveFeatures.setNumOfDivelets(1);
				diveFeatures.setSurfaceIntervalTime(0);
			}else
				diveFeatures.setNumOfDivelets(2);
			//System.out.println("divelet count: "+diveFeatures.getNumOfDivelets());
			
			diveFeatures.setFirstDivelet(extractFirstDivelet(diveletLimitPoints));
			diveFeatures.setSecondDivelet(extractSecondDivelet(diveletLimitPoints));
			//System.out.println("second: "+diveletLimitPoints);
			
			if(diveFeatures.getNumOfDivelets()==2){
				firstDiveletFeatures.setDiveDepth(Utils.myMax(diveFeatures.getFirstDivelet().values()));
				firstDiveletFeatures.setBottomTime(computeBottomTime(diveFeatures.getFirstDivelet()));
				firstDiveletFeatures.setAscentSpeed(computeAscentSpeed(diveFeatures.getFirstDivelet()));
				diveFeatures.setFirstDiveletFeatures(firstDiveletFeatures);
				
				secondDiveletFeatures.setDiveDepth(Utils.myMax(diveFeatures.getSecondDivelet().values()));
				secondDiveletFeatures.setBottomTime(computeBottomTime(diveFeatures.getSecondDivelet()));
				secondDiveletFeatures.setAscentSpeed(computeAscentSpeed(diveFeatures.getSecondDivelet()));
				diveFeatures.setSecondDiveletFeatures(secondDiveletFeatures);
				
			}
			else{
				firstDiveletFeatures.setDiveDepth(Utils.myMax(diveFeatures.getFirstDivelet().values()));
				firstDiveletFeatures.setBottomTime(computeBottomTime(diveFeatures.getFirstDivelet()));
				firstDiveletFeatures.setAscentSpeed(computeAscentSpeed(diveFeatures.getFirstDivelet()));
				diveFeatures.setFirstDiveletFeatures(firstDiveletFeatures);
				diveFeatures.setSecondDiveletFeatures(null);
			}
		}
		return diveFeatures;
	}
	
	
	List<Long> computeLimitsOfDivelets(List<Long> diveletLimitPoints){
	    //collect all the points when the depth is just greater than or less than PADITable.SafeDepthMinimumInMeters
	    boolean matchFlag = false;
	    for(Iterator<Map.Entry<Long,Double>> i = (Iterator<Map.Entry<Long,Double>>)diveFeatures.getDiveProfile().entrySet().iterator();i.hasNext();){
	      Map.Entry<Long,Double> e = i.next();
	      Long K = e.getKey();
	      Double V = e.getValue();
	      if (matchFlag == false) {
	        //collect the point for descent
	        if (V.doubleValue() >= PADITable.safeDepthMinimumInMeters){
	          diveletLimitPoints.add(K);
	          matchFlag = true;
	        }
	      }
	      else{
	        //collect the point for ascent
	        if(V.doubleValue()<=0.15*diveFeatures.diveDepth){//.safeDepthMinimumInMeters)0.15*diveDepth{
	          diveletLimitPoints.add(K);
	          matchFlag = false;
	        }
	      }
	    }
	    return diveletLimitPoints;
	  }
	
	Long getSurfacePoint(TreeMap<Long, Double> map){
	    for(Iterator<Entry<Long, Double>> i = map.entrySet().iterator();i.hasNext();){
	      Map.Entry<Long,Double> e = (Map.Entry<Long,Double>)i.next();
	      Long K = (Long)e.getKey();
	      Double V = (Double)e.getValue();
	      if(V.doubleValue()==0)
	        return K;
	    }
	    return null;
	  }

	
	List<Long> refineLimitsOfDivelets(List<Long> diveletLimitPoints){
	    //extract the surface points
	    List<Long> rtn = new ArrayList<Long>();
	    Long first = diveFeatures.getDiveProfile().firstKey();
	    rtn.add(first);
	    
	    int i = 1;//because initial point is already added to rtn
	    while(i<diveletLimitPoints.size()-1){
	    	Long K1 = diveletLimitPoints.get(i);
	      i++;
	      Long K2 = diveletLimitPoints.get(i);
	      TreeMap<Long, Double> subMap = new TreeMap<Long, Double>();
	      subMap.putAll(diveFeatures.getDiveProfile().subMap(K1,K2));
	      Long sPoint = getSurfacePoint(subMap);
	      if(sPoint!=null)
	      rtn.add(sPoint);
	      i++;
	    }
	    rtn.add(diveFeatures.getDiveProfile().lastKey());
	    return rtn;
	  }

	TreeMap<Long,Double> extractFirstDivelet(List<Long> diveletLimitPoints){
		Long K1 = diveletLimitPoints.get(0);
	    Long K2 = diveletLimitPoints.get(1);
	    	
	    TreeMap<Long,Double> firstDivelet = new TreeMap<Long,Double>();
	    firstDivelet.putAll(diveFeatures.getDiveProfile().subMap((Long)K1, (Long)K2));
		firstDivelet.put(K2,diveFeatures.getDiveProfile().get(K2));
		return firstDivelet;
	}
	
	TreeMap<Long,Double> extractSecondDivelet(List<Long> diveletLimitPoints){
		 Long K = diveletLimitPoints.get(1);
		    
		 TreeMap<Long,Double> secondDivelet = new TreeMap<Long,Double>();
		secondDivelet.putAll(diveFeatures.getDiveProfile().tailMap(K));
	    return secondDivelet;
	  }
	
	double computeAscentSpeed(TreeMap<Long,Double> divelet){
		double diveletDepth = Utils.myMax(divelet.values());
	    Long timeAtDeepest = computeTimeAtDepth(divelet,diveletDepth);
		TreeMap<Long,Double> ascentProfile = new TreeMap<Long,Double>();
		ascentProfile.putAll(divelet.tailMap(timeAtDeepest));
		
		double speed = 60*(ascentProfile.get(ascentProfile.firstKey())-ascentProfile.get(ascentProfile.lastKey()))/(ascentProfile.lastKey()-ascentProfile.firstKey());
		//System.out.println("Ascent Speed: "+speed);
		return (speed);
	}
	
	long computeBottomTime(TreeMap<Long,Double> divelet){
	    Long d1 = (Long)getKeyGreaterThan(divelet,PADITable.depthEquivalence3);//
	    double diveletDepth = Utils.myMax(divelet.values());
	    Long timeAtDeepest = computeTimeAtDepth(divelet,diveletDepth);
		TreeMap<Long,Double> ascentProfile = new TreeMap<Long,Double>();
		ascentProfile.putAll(divelet.tailMap(timeAtDeepest));
		Long d2 = (Long)getKeyLessThan(ascentProfile,0.85*diveletDepth);
	   //System.out.println("start"+d1+"end"+d2);
	    long x = d2-d1;
	    double y = (0.15*diveletDepth)/6;//6m/min ascent rate is assumed
	    //System.out.println("bottom time delta: "+y);
	    Long X = new Long(Math.round((x-y)/60));
	    return X.longValue();//returned in minutes
	  }
	
	Long computeTimeAtDepth(TreeMap<Long,Double> divelet,double depth){
	    //iterate through the dive and return the time for the given depth 
	    for(Iterator<Entry<Long, Double>> i = divelet.entrySet().iterator();i.hasNext();){
	      Map.Entry<Long, Double> e = (Map.Entry<Long, Double>)i.next();
	      Double V = (Double)e.getValue();
	      if(V.doubleValue()==depth)
	        return (Long)e.getKey();
	    }
	    return null;
	  }
	
	Long getKeyLessThan(TreeMap<Long, Double> divelet, double depth) {
		//iterate through the dive and return the time just before the given depth
	    for(Iterator<Entry<Long, Double>> i = divelet.entrySet().iterator();i.hasNext();){
	      Map.Entry<Long, Double> e = (Map.Entry<Long,Double>)i.next();
	      Double V = (Double)e.getValue();
	      if(V.doubleValue()<=depth)
	        return e.getKey();
	    }
		return null;
	}

	Long getKeyGreaterThan(TreeMap<Long,Double> divelet,double depth) {
		//iterate through the dive and return the time just after the given depth 
	    for(Iterator<Entry<Long, Double>> i = divelet.entrySet().iterator();i.hasNext();){
	      Map.Entry<Long, Double> e = (Map.Entry<Long, Double>)i.next();
	      Double V = (Double)e.getValue();
	      if(V.doubleValue()>=depth)
	        return e.getKey();
	    }
		return divelet.firstKey();
	}

}
