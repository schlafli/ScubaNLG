package messages;

public class DiveletAscentRateEvaluationMessage extends DiveletMessage {
	
	private AscentRateEvaluationValue	evaluation;
	private double						ascentRate;
	
	public AscentRateEvaluationValue getEvaluation() {
		return evaluation;
	}
	
	public void setEvaluation(AscentRateEvaluationValue evaluation) {
		this.evaluation = evaluation;
	}
	
	public double getAscentRate() {
		return ascentRate;
	}
	
	public void setAscentRate(double ascentRate) {
		this.ascentRate = ascentRate;
	}
	
}
