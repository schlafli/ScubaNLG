package analytics;
import java.util.TreeMap;

/**
 * 
 */

/**
 * This class stores all the features extracted by DiveAnalyser and DiveInterpreter. NLG module receives objects of DiveFeature as the only input.
 * @author csc242
 *
 */
public class DiveFeatures {

	Double diveNo;
	double diveDepth;
	boolean realDive;
	long surfaceIntervalTime;
	int numOfDivelets;
	char startPressureGroup;
	char endPressureGroup;
	TreeMap<Long, Double> diveProfile;
	TreeMap<Long, Double> firstDivelet;
	TreeMap<Long, Double> secondDivelet;
	DiveletFeatures firstDiveletFeatures;
	DiveletFeatures secondDiveletFeatures;
	/**
	 * @return the diveNo
	 */
	public Double getDiveNo() {
		return diveNo;
	}
	/**
	 * @param diveNo the diveNo to set
	 */
	public void setDiveNo(Double diveNo) {
		this.diveNo = diveNo;
	}
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
	 * @return the realDive
	 */
	public boolean isRealDive() {
		return realDive;
	}
	/**
	 * @param realDive the realDive to set
	 */
	public void setRealDive(boolean realDive) {
		this.realDive = realDive;
	}
	/**
	 * @return the surfaceIntervalTime
	 */
	public long getSurfaceIntervalTime() {
		return surfaceIntervalTime;
	}
	/**
	 * @param surfaceIntervalTime the surfaceIntervalTime to set
	 */
	public void setSurfaceIntervalTime(long surfaceIntervalTime) {
		this.surfaceIntervalTime = surfaceIntervalTime;
	}
	/**
	 * @return the numOfDivelets
	 */
	public int getNumOfDivelets() {
		return numOfDivelets;
	}
	/**
	 * @param numOfDivelets the numOfDivelets to set
	 */
	public void setNumOfDivelets(int numOfDivelets) {
		this.numOfDivelets = numOfDivelets;
	}
	/**
	 * @return the diveProfile
	 */
	public TreeMap<Long, Double> getDiveProfile() {
		return diveProfile;
	}
	/**
	 * @param diveProfile the diveProfile to set
	 */
	public void setDiveProfile(TreeMap<Long, Double> diveProfile) {
		this.diveProfile = diveProfile;
	}
	/**
	 * @return the firstDivelet
	 */
	public TreeMap<Long, Double> getFirstDivelet() {
		return firstDivelet;
	}
	/**
	 * @param firstDivelet the firstDivelet to set
	 */
	public void setFirstDivelet(TreeMap<Long, Double> firstDivelet) {
		this.firstDivelet = firstDivelet;
	}
	/**
	 * @return the secondDivelet
	 */
	public TreeMap<Long, Double> getSecondDivelet() {
		return secondDivelet;
	}
	/**
	 * @param secondDivelet the secondDivelet to set
	 */
	public void setSecondDivelet(TreeMap<Long, Double> secondDivelet) {
		this.secondDivelet = secondDivelet;
	}
	/**
	 * @return the firstDiveletFeatures
	 */
	public DiveletFeatures getFirstDiveletFeatures() {
		return firstDiveletFeatures;
	}
	/**
	 * @param firstDiveletFeatures the firstDiveletFeatures to set
	 */
	public void setFirstDiveletFeatures(DiveletFeatures firstDiveletFeatures) {
		this.firstDiveletFeatures = firstDiveletFeatures;
	}
	/**
	 * @return the secondDiveletFeatures
	 */
	public DiveletFeatures getSecondDiveletFeatures() {
		return secondDiveletFeatures;
	}
	/**
	 * @param secondDiveletFeatures the secondDiveletFeatures to set
	 */
	public void setSecondDiveletFeatures(DiveletFeatures secondDiveletFeatures) {
		this.secondDiveletFeatures = secondDiveletFeatures;
	}
	/**
	 * @return the startPressureGroup
	 */
	public char getStartPressureGroup() {
		return startPressureGroup;
	}
	/**
	 * @param startPressureGroup the startPressureGroup to set
	 */
	public void setStartPressureGroup(char startPressureGroup) {
		this.startPressureGroup = startPressureGroup;
	}
	/**
	 * @return the endPressureGroup
	 */
	public char getEndPressureGroup() {
		return endPressureGroup;
	}
	/**
	 * @param endPressureGroup the endPressureGroup to set
	 */
	public void setEndPressureGroup(char endPressureGroup) {
		this.endPressureGroup = endPressureGroup;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiveFeatures [<br>"
				+ (diveNo != null ? "diveNo=" + diveNo + ", " : "")
				+ "<br>diveDepth="
				+ diveDepth
				+ ",<br>realDive="
				+ realDive
				+ ",<br>surfaceIntervalTime="
				+ surfaceIntervalTime
				+ ",<br>numOfDivelets="
				+ numOfDivelets
				+ ",<br>startPressureGroup="
				+ startPressureGroup
				+ ",<br>endPressureGroup="
				+ endPressureGroup
				+ ",<br>"
				+ (firstDiveletFeatures != null ? "firstDiveletFeatures="
						+ firstDiveletFeatures + ", <br>" : "")
				+ (secondDiveletFeatures != null ? "secondDiveletFeatures="
						+ secondDiveletFeatures : "") + "]";
	}
	
}
