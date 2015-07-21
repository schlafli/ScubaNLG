package analytics;
/**
 * 
 */

/**
 * This class stores features for individuals divelets, extracted from a dive profile by DiveAnalyser. DiveDepth, BottomTime and AscentSpeed are assumed to represent all the key information about a divelet. Additional features such as rapid ascent events rather the overall ascent speed could be extracted for richer and more accurate review of a dive.
 * @author csc242
 *
 */
public class DiveletFeatures {

	double diveDepth;
	long bottomTime;
	double ascentSpeed;
	double excessDiveDepth;
	long excessBottomTime;
	double excessAscentSpeed;
	boolean safetyStopRequired;
	
	
	/**
	 * @return the diveDepth
	 */
	public double getDiveDepth() {
		return diveDepth;
	}
	/**
	 * @param diveDepth the diveDepth to set
	 */
	public void setDiveDepth(double diveDepth) {
		this.diveDepth = diveDepth;
	}
	/**
	 * @return the bottomTime
	 */
	public long getBottomTime() {
		return bottomTime;
	}
	/**
	 * @param bottomTime the bottomTime to set
	 */
	public void setBottomTime(long bottomTime) {
		this.bottomTime = bottomTime;
	}
	/**
	 * @return the ascentSpeed
	 */
	public double getAscentSpeed() {
		return ascentSpeed;
	}
	/**
	 * @param ascentSpeed the ascentSpeed to set
	 */
	public void setAscentSpeed(double ascentSpeed) {
		this.ascentSpeed = ascentSpeed;
	}
	/**
	 * @return the excessDiveDepth
	 */
	public double getExcessDiveDepth() {
		return excessDiveDepth;
	}
	/**
	 * @param excessDiveDepth the excessDiveDepth to set
	 */
	public void setExcessDiveDepth(double excessDiveDepth) {
		this.excessDiveDepth = excessDiveDepth;
	}
	/**
	 * @return the excessBottomTime
	 */
	public long getExcessBottomTime() {
		return excessBottomTime;
	}
	/**
	 * @param excessBottomTime the excessBottomTime to set
	 */
	public void setExcessBottomTime(long excessBottomTime) {
		this.excessBottomTime = excessBottomTime;
	}
	/**
	 * @return the excessAscentSpeed
	 */
	public double getExcessAscentSpeed() {
		return excessAscentSpeed;
	}
	/**
	 * @param excessAscentSpeed the excessAscentSpeed to set
	 */
	public void setExcessAscentSpeed(double excessAscentSpeed) {
		this.excessAscentSpeed = excessAscentSpeed;
	}
	/**
	 * @return the safetyStopRequired
	 */
	public boolean isSafetyStopRequired() {
		return safetyStopRequired;
	}
	/**
	 * @param safetyStopRequired the safetyStopRequired to set
	 */
	public void setSafetyStopRequired(boolean safetyStopRequired) {
		this.safetyStopRequired = safetyStopRequired;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiveletFeatures [<br>diveDepth=" + diveDepth + ", <br>bottomTime="
				+ bottomTime + ",<br>ascentSpeed=" + ascentSpeed
				+ ",<br>excessDiveDepth=" + excessDiveDepth
				+ ",<br>excessBottomTime=" + excessBottomTime
				+ ",<br>excessAscentSpeed=" + excessAscentSpeed
				+ ",<br>safetyStopRequired=" + safetyStopRequired + "]";
	}
	

}
