package messages;

public class DiveletExcessDepthTimeMessage extends DiveletMessage {
	
	private int	excessTime;
	private int	diveDepth;
	
	public int getDiveDepth() {
		return diveDepth;
	}
	
	public void setDiveDepth(int diveDepth) {
		this.diveDepth = diveDepth;
	}
	
	public int getExcessTime() {
		return excessTime;
	}
	
	public void setExcessTime(int excessTime) {
		this.excessTime = excessTime;
	}
	
}
