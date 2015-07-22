package messages;

public class DiveletDepthWarningMessage extends DiveletMessage {
	
	private boolean	closeToLimit;
	private double	excessDiveDepth;
	
	public boolean isCloseToLimit() {
		return closeToLimit;
	}
	
	public void setCloseToLimit(boolean closeToLimit) {
		this.closeToLimit = closeToLimit;
	}
	
	public double getExcessDiveDepth() {
		return excessDiveDepth;
	}
	
	public void setExcessDiveDepth(double excessDiveDepth) {
		this.excessDiveDepth = excessDiveDepth;
	}
	
}
